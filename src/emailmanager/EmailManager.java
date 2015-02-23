package emailmanager;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class EmailManager extends JFrame
        implements ActionListener {
    private Buttons list = new Buttons("Refresh", "resource/refresh.png", true);
    private Buttons quit = new Buttons("Quit", "resource/close.png", true);
    private Buttons read = new Buttons("Read", "resource/open-message.png", true);
    private Buttons newMessage = new Buttons("New", "resource/edit.png", true);
    private Buttons label = new Buttons("Add Label", "resource/event.png", true);
    // load icons
    
    
    
    private JTextField idTextField = new JTextField(2);
    private static JTextArea textArea = new JTextArea(); // change this back to private
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private static String[] defaultLabels = {"", "Work", "Important", "Todo"};
    public static ArrayList<String> labels = new ArrayList<String>(Arrays.asList(defaultLabels));
    public static void main(String[] args) {
        new EmailManager();
    }

    
    public EmailManager() {
        setLayout(new BorderLayout());
        setSize(600, 300);
        setResizable(false);
        setTitle("Email Manager");
        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel top = new JPanel();
        top.add(list);
        list.addActionListener(this);
        top.add(read);
        read.addActionListener(this);
        top.add(idTextField);
        top.add(label);
        label.addActionListener(this);
        top.add(newMessage);
        newMessage.addActionListener(this);
        top.add(quit);
        quit.addActionListener(this);
        add("North", top);

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(560, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(560, 200));
        
        JPanel middle = new JPanel();
        middle.add(scrollPane);
        add("Center", middle);

        textArea.setText(MessageData.listAll());

        setVisible(true);
    }

    public static void refresh() {
        textArea.setText(MessageData.listAll());
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            refresh();
        } else if (e.getSource() == read) {
            new ReadMessage(idTextField.getText());
        } else if (e.getSource() == newMessage) {
            new NewMessage();
        } else if (e.getSource() == label) {
            new LabelMessages(idTextField.getText());
        } else if (e.getSource() == quit) {
            //MessageData.reset(); // Take this out
            MessageData.close();
            System.exit(0);
        }
    }
}