package emailmanager;

// Import required packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReadMessage extends JFrame
        implements ActionListener {

    
    // Define our variables and create our objects
    private String id;
    private JTextField priority = new JTextField(2); // Create a text field object
    private JButton update = new JButton("Update"); // Create a button object
    private JButton close = new JButton("Close");
    private JTextArea textArea = new JTextArea(); // Create a text area object
    private JScrollPane scrollPane = new JScrollPane(textArea); // Create a scroll pane object

    // Define the constructor
    public ReadMessage(String id) {
        this.id = id; // grab the id of the email from the selected object

        // Set up the layout
        setLayout(new BorderLayout()); // Use a border layout
        setSize(500, 250); // Set the window size to 500x250
        setResizable(false); // Make it fixed-sized
        setTitle("Message Details"); // Set the title of the window

        // Draw the components
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We only want to terminate the program when no JFrames are visible
        JPanel top = new JPanel(); // Create a JPanel object
        // Add some components to the JPanel
        top.add(new JLabel("Enter Priority (1-5):"));
        top.add(priority);
        top.add(update);
        update.addActionListener(this); // Add an action listener to the button with the object that called it as an argument
        top.add(close);
        close.addActionListener(this); // Add another action listener to the button
        add("North", top); // Draw the JPanel at the top (North borderlayout)

        // TEXT!
        // SCROLL BARS!
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); // Set the text for our text area to a not so stylish monospace font.
        textArea.setLineWrap(true); // Enable automatic line wrapping
        textArea.setWrapStyleWord(true); // Wrap around words instead of chars.
        textArea.setPreferredSize(new Dimension(450, 150)); // Set the text area size to 450x150
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show the vertical scrollbar
        scrollPane.setPreferredSize(new Dimension(450, 150)); // Set the scroll pane to 450x150

        JPanel middle = new JPanel(); // Create a JPanel object
        middle.add(scrollPane); // add the scroll pane to our middle JPanel
        add("Center", middle); // Draw the middle JPane in the center

        displayMessage(); // Call displayMessage once to populate the text area
        
        setVisible(true); // LET THERE BE EMAIL (make everything visible)
    }

    // Our event handler
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) { // If the update button was pressed
            int priorityValue = Integer.parseInt(priority.getText()); // Parse the priority entered by user as an Integer
            MessageData.setPriority(id, priorityValue); // Set the priority of the message
            displayMessage(); // Redraw the text area to reflect the changes
        } else if (e.getSource() == close) { // If the close button was pressed
            dispose(); // Close the JFrame
        }
    }

    // Our displayMessage method
    private void displayMessage() {
        String subject = MessageData.getSubject(id); // Put the subject of a message with given ID into a var
        if (subject == null) { // if that message doesn't actually exist
            textArea.setText("No such message"); // Tell the user they're doing it wrong
        } else {
            // Populate the text area with the message details
            textArea.setText("Subject: " + subject); // Show the subject in the text area
            textArea.setText("From: " + MessageData.getSender(id)); // for some reason, immediately replace the subject with the message sender
            // These append as opposed to replace so we can see them all
            textArea.append("\nTo: " + MessageData.getRecipient(id)); // Append who the message was to, in case you forgot your name
            textArea.append("\nPriority: " + MessageData.stars(MessageData.getPriority(id))); // Append the priority of the message
            textArea.append("\n\n" + MessageData.getMessage(id)); // Append two newlines and then the message body
        }
    }
}