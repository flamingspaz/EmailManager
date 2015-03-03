package emailmanager;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class EmailManager extends JFrame
        implements ActionListener {
    private final Buttons list = new Buttons("Refresh", "resource/refresh.png", true);
    private final Buttons quit = new Buttons("Quit", "resource/close.png", true);
    private final Buttons newMessage = new Buttons("New", "resource/edit.png", true);
    private final Buttons label = new Buttons("Add Label", "resource/event.png", true);
    // load icons
    private static ResultSetTable rst = MessageData.listAllRS();

    private static final JScrollPane scrollPane = new JScrollPane(rst);
    
    private static final String[] defaultLabels = {"", "Work", "Important", "Todo"};
    public static ArrayList<String> labels = new ArrayList<String>(Arrays.asList(defaultLabels));
    ReadMessage readMessagePanel = new ReadMessage("");
    public static void main(String[] args) {
        new EmailManager();
    }

    
    public EmailManager() {
        setLayout(new BorderLayout());
        setSize(1200, 600);
        setResizable(false);
        setTitle("Email Manager");
        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        rst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // some jtable housekeeping, to make it look nice.
        rst.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                readMessagePanel.displayMessage(rst.getValueAt(rst.getSelectedRow(), 0).toString());
            }
        });
        JPanel top = new JPanel(new FlowLayout());
        top.add(list);
        list.addActionListener(this);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(label);
        label.addActionListener(this);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(newMessage);
        newMessage.addActionListener(this);
        newMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(quit);
        quit.addActionListener(this);
        quit.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        add("North", top);        
   
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(650, 490));
        
        JPanel west = new JPanel();
        west.add(scrollPane);
        add("West", west);
        JPanel east = new JPanel();
        east.add(readMessagePanel);
        add("East", east);
        setVisible(true);
    }

    public static void refresh() {
        // need to do this
        scrollPane.getViewport().remove(rst);
        scrollPane.getViewport().add(MessageData.listAllRS());
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            refresh();
        } else if (e.getSource() == newMessage) {
            new NewMessage();
        } else if (e.getSource() == label) {
            new LabelMessages(rst.getValueAt(rst.getSelectedRow(), 0).toString());
        } else if (e.getSource() == quit) {
            //MessageData.reset(); // Take this out
            MessageData.close();
            System.exit(0);
        }
    }
}