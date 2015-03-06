package emailmanager;

// Import required packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReadMessage extends JPanel
        implements ActionListener {

    
    // Define our variables and create our objects
    private String id;
    private JTextField priority = new JTextField(2); // Create a text field object
    private Buttons update = new Buttons("", "resource/save.png", false, "Update Priority");
    private final Buttons label = new Buttons("", "resource/event.png", true, "Add Label");
    private Buttons delete = new Buttons("", "resource/delete.png", false, "Delete Message");
    private JTextArea textArea = new JTextArea(); // Create a text area object
    private JScrollPane scrollPane = new JScrollPane(textArea); // Create a scroll pane object
    ImageIcon infoIcon = new ImageIcon("resource/alert.png");
    private String prid;
    private JLabel detailsLabel = new JLabel("<html>From: <br/>To:  <br/>Priority:  <br/><br/>Subject: </html>", SwingConstants.LEFT);
    // Define the constructor
    public ReadMessage(String id) {
        // Set up the layout
        setLayout(new BorderLayout()); // Use a border layout
        setSize(1200, 250); // Set the window size to 500x250
        
        // Draw the components
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Create a JPanel object
        // Add some components to the JPanel
        top.add(new JLabel("Enter Priority (1-5):"));
        
        label.addActionListener(this);
        top.add(priority);
        top.add(update);
        top.add(label);
        top.add(delete);
        
        update.setPreferredSize(new Dimension(32, 32));
        label.setPreferredSize(new Dimension(32, 32));
        delete.setPreferredSize(new Dimension(32, 32));
        update.addActionListener(this); // Add an action listener to the button with the object that called it as an argument
        delete.addActionListener(this);
        add("Center", top); // Draw the JPanel at the top (North borderlayout)

        // TEXT!
        // SCROLL BARS!
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); // Set the text for our text area to a not so stylish monospace font.
        textArea.setLineWrap(true); // Enable automatic line wrapping
        textArea.setWrapStyleWord(true); // Wrap around words instead of chars.
        textArea.setPreferredSize(new Dimension(500, 400)); // Set the text area size to 450x150
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show the vertical scrollbar
        scrollPane.setPreferredSize(new Dimension(500, 350)); // Set the scroll pane to 450x150

        JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Create a JPanel object

        middle.add(detailsLabel);
        add("North", middle); // Draw the middle JPane in the center
        
        JPanel bottom = new JPanel(); // Create a JPanel object
        bottom.add(scrollPane); // add the scroll pane to our middle JPanel
        add("South", bottom); // Draw the middle JPane in the center

        displayMessage(id); // Call displayMessage once to populate the text area
        
        setVisible(true); // LET THERE BE EMAIL (make everything visible)
    }

    // Our event handler
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) { // If the update button was pressed
            try {
                
                int priorityValue = Integer.parseInt(priority.getText()); // Parse the priority entered by user as an Integer
                if (priorityValue <= 5 && priorityValue >= 1) {
                    MessageData.setPriority(prid, priorityValue); // Set the priority of the message
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 5", "Error", JOptionPane.INFORMATION_MESSAGE, infoIcon);
                }
                
            }
            catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.INFORMATION_MESSAGE, infoIcon);
            }
            displayMessage(prid); // Redraw the text area to reflect the changes
            EmailManager.refresh(); // redraw main window
        }
        else if (e.getSource() == delete) {
            MessageData.deleteMessage(prid);
            EmailManager.refresh();
            displayMessage(Integer.toString(Integer.parseInt(prid) - 1)); // if possible, show the last message
        } else if (e.getSource() == label) {
            EmailManager.addLabel();
        } 
    }

    // Our displayMessage method
    public void displayMessage(String id) {
        prid = id;
        String subject = MessageData.getSubject(id); // Put the subject of a message with given ID into a var
        if (subject == null) { // if that message doesn't actually exist
            textArea.setText("No such message"); // Tell the user they're doing it wrong
        } else {
            // Populate the text area with the message details
            detailsLabel.setText("<html>From: " + MessageData.getSender(id) + "<br/>To: " + MessageData.getRecipient(id) + "<br/>Priority: " + MessageData.stars(MessageData.getPriority(id)) + "<br/><br/>Subject: " + subject + "</html>");
            textArea.setText("\n" + MessageData.getMessage(id)); // Append two newlines and then the message body
        }
    }
}