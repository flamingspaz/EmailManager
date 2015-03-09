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
public class LabelMessages extends JFrame implements ActionListener {

    // define our components
    private final JTextField msgId = new JTextField(2);
    private final JComboBox label = new JComboBox(EmailManager.labels.toArray());
    private final Buttons addBtn = new Buttons("", "resource/add.png", false, "Add Label");
    private final Buttons closeBtn = new Buttons(" Close", "resource/cancel.png", false, "Close");
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(textArea);
    private String id;
    ImageIcon infoIcon = new ImageIcon("resource/alert.png");

    public LabelMessages(String labelId) {
        id = labelId;

        setLayout(new BorderLayout());
        setSize(600, 300);
        setResizable(false);
        setTitle("Label Messages");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(EmailManager.logoIcon.getImage());
        addBtn.setPreferredSize(new Dimension(32, 32));
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Message ID: ", SwingConstants.RIGHT));
        top.add(msgId);
        top.add(new JLabel("Label: ", SwingConstants.RIGHT));
        top.add(label);
        label.setEditable(true);
        top.add(addBtn);
        top.add(closeBtn);
        add("North", top);
        msgId.setText(id); // if the user has already entered an id, lets carry it over

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(560, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(560, 200));

        JPanel middle = new JPanel();
        middle.add(scrollPane);
        add("Center", middle);
        closeBtn.addActionListener(this);
        addBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeBtn) {
            setVisible(false);
            dispose();
        }
        if (e.getSource() == addBtn) {
            boolean exists = false;
            for (int i = 0; i < label.getItemCount(); i++) {
                // does this label aready exist in the jcombobox?
                if (label.getItemAt(i) == label.getSelectedItem().toString()) {
                    exists = true; // it does!
                }
            }
            if (!exists) {
                // it doesn't? let's add it in.
                label.addItem(label.getSelectedItem().toString());
                EmailManager.labels.add(label.getSelectedItem().toString());
            }
            if (MessageData.getMessage(msgId.getText()) != null) {
                // used to check for empty label, see commit f3b58d5
                // and then add our label to the message.
                MessageData.setLabel(msgId.getText(), label.getSelectedItem().toString());
                textArea.setText(MessageData.listLabelled(label.getSelectedItem().toString()));
                EmailManager.refresh();
            } else {
                // you dun goofed
                JOptionPane.showMessageDialog(null, "Please enter a valid Message ID.", "Error", JOptionPane.INFORMATION_MESSAGE, infoIcon);
            }
        }
    }

}
