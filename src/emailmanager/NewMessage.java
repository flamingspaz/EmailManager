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
    
    private JTextField msgId = new JTextField(2);
    private JTextField recipient = new JTextField(30);
    private JTextField subjectTxt = new JTextField(30);
    private JButton sendBtn = new JButton("Send"); 
    private JButton discardBtn = new JButton("Discard");
    private JTextArea textArea = new JTextArea(); 
    private JScrollPane scrollPane = new JScrollPane(textArea); 
    
    private void confirmClose() {
            int checkIfRlySure = JOptionPane.showConfirmDialog(null, "Are you sure you want to discard your email?","Warning",JOptionPane.YES_NO_OPTION);
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
        
        // Layout the top
        JPanel top = new JPanel(new GridLayout(3, 0));
        top.add(new JLabel("Message ID: ", SwingConstants.RIGHT));
        top.add(msgId);
        top.add(new JLabel("To: ", SwingConstants.RIGHT));
        top.add(recipient);
        top.add(new JLabel("Subject: ", SwingConstants.RIGHT));
        top.add(subjectTxt);
        add("North", top);
        
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e)
        {
           confirmClose();
        }});
        
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
        }
        else if (e.getSource() == sendBtn) {
            if (subjectTxt.getText().toString().isEmpty()) {
                int noSubConfirm = JOptionPane.showConfirmDialog(null, "This message does not have a subject. Are you sure you want to send it?", "No Subject", JOptionPane.YES_NO_OPTION);
                if (noSubConfirm == JOptionPane.YES_OPTION) {
                    // send email
                }
                // send email
            }
        }
    }
    
}
