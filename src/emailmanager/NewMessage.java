/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailmanager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author yousef
 */
public class NewMessage extends JFrame implements ActionListener {

    // define our components
    private final JTextField recipient = new JTextField(30);
    private final JTextField subjectTxt = new JTextField(30);
    private final Buttons sendBtn = new Buttons(" Send", "resource/foward.png", false, "Send Message");
    private final Buttons discardBtn = new Buttons(" Discard", "resource/cancel.png", false, "Discard Message");
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(textArea);
    ImageIcon realInfoIcon = new ImageIcon("resource/information.png");

    private void confirmClose() {
        // make sure we actually want to close this window, someone might have typed out a novel.
        int checkIfRlySure = JOptionPane.showConfirmDialog(null, "Are you sure you want to discard your email?", "Warning", JOptionPane.YES_NO_OPTION, 0, realInfoIcon);
        if (checkIfRlySure == 0) {
            setVisible(false);
            dispose();
        }
    }

    public NewMessage() {
        setLayout(new BorderLayout());
        setSize(600, 400);
        setResizable(false);
        setTitle("New Message");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(EmailManager.logoIcon.getImage());
        // Layout the top
        JPanel top = new JPanel(new GridLayout(2, 0));
        top.add(new JLabel("To: ", SwingConstants.RIGHT));
        top.add(recipient);
        top.add(new JLabel("Subject: ", SwingConstants.RIGHT));
        top.add(subjectTxt);
        add("North", top);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmClose();
            }
        });

        sendBtn.addActionListener(this);

        JPanel middle = new JPanel();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(590, 250));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(590, 250));

        middle.add(scrollPane);
        add("Center", middle);

        JPanel bottom = new JPanel();
        bottom.add(sendBtn);
        bottom.add(discardBtn);
        add("South", bottom);
        discardBtn.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == discardBtn) {
            confirmClose();
        } else if (e.getSource() == sendBtn) {
            if (subjectTxt.getText().toString().isEmpty()) { // does this email have an empty subject?
                int noSubConfirm = JOptionPane.showConfirmDialog(null, "This message does not have a subject. Are you sure you want to send it?", "No Subject", JOptionPane.YES_NO_OPTION, 0, realInfoIcon);
                if (noSubConfirm == JOptionPane.YES_OPTION) {
                    MessageData.sendMessage(recipient.getText(), "Chris@redwich.ac.uk", subjectTxt.getText(), textArea.getText()); // send with no subject if you say so, user!
                    EmailManager.refresh();
                    setVisible(false);
                    dispose();
                }
            } else {
                // everything looks good, send off the message.
                MessageData.sendMessage(recipient.getText(), "Chris@redwich.ac.uk", subjectTxt.getText(), textArea.getText());
                EmailManager.refresh();
                setVisible(false);
                dispose();
            }
        }
    }

}
