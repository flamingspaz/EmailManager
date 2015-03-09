package emailmanager;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EmailManager extends JFrame
        implements ActionListener {

    // load our fancy buttons
    private final Buttons list = new Buttons("Refresh", "resource/refresh.png", true, "Refresh");
    private final Buttons quit = new Buttons("Quit", "resource/close.png", true, "Quit");
    private final Buttons newMessage = new Buttons("New", "resource/edit.png", true, "New");
    // load our messages table
    private static ResultSetTable rst = MessageData.listAllRS();

    private static final JScrollPane scrollPane = new JScrollPane(rst);

    private static final String[] defaultLabels = {"", "Work", "Important", "Todo"};
    public static ArrayList<String> labels = new ArrayList<String>(Arrays.asList(defaultLabels));
    static ReadMessage readMessagePanel = new ReadMessage("");
    public static ImageIcon logoIcon = new ImageIcon("resource/logo.png");

    public static void main(String[] args) {
        new EmailManager();
    }

    public EmailManager() {
        // basic layout and decoration
        setLayout(new BorderLayout());
        setSize(1200, 600);
        setResizable(false);
        setTitle("Email Manager");
        setIconImage(logoIcon.getImage());
        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // make a panel and add components to it
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(list);
        list.addActionListener(this);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(newMessage);
        newMessage.addActionListener(this);
        newMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(quit);
        quit.addActionListener(this);
        quit.setAlignmentX(Component.RIGHT_ALIGNMENT);

        add("North", top);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(670, 500));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel west = new JPanel();
        west.add(scrollPane);
        add("West", west);
        JPanel east = new JPanel();
        east.add(readMessagePanel);
        add("East", east);
        refresh();
        setVisible(true);
    }

    public static void refresh() {
        // refresh our JTable with new data
        scrollPane.getViewport().remove(rst);
        rst = MessageData.listAllRS();
        rst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.getViewport().add(rst);
        rst.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                readMessagePanel.displayMessage(rst.getValueAt(rst.getSelectedRow(), 0).toString());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            refresh();
        } else if (e.getSource() == newMessage) {
            new NewMessage();
        } else if (e.getSource() == quit) {
            //MessageData.reset(); // uncomment this if you want to reset all messages in the db to the default ones
            MessageData.close();
            System.exit(0);
        }
    }

    public static void addLabel() {
        // create a label window with the selected message ID already inputted
        new LabelMessages(rst.getValueAt(rst.getSelectedRow(), 0).toString());
    }
}
