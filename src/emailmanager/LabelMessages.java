/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailmanager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 *
 * @author yousef
 */
public class LabelMessages extends JFrame implements ActionListener {

    public static String[] labels = {"", "Work", "Home", "Todo"}; //use this to store labels at a later point.
    private JTextField msgId = new JTextField(2);
    private JComboBox label = new JComboBox(labels);
    private JButton addBtn = new JButton("Add Label");
    private JButton closeBtn = new JButton("Close");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private String id;

    public LabelMessages(String labelId) {
        id = labelId;

        setLayout(new BorderLayout());
        setSize(600, 300);
        setResizable(false);
        setTitle("Label Messages");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        textArea.setText(MessageData.listAll());

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeBtn) {
            setVisible(false);
            dispose();
        }
        if (e.getSource() == addBtn) {
            
            boolean exists = false;
            for (int i = 0; i < label.getItemCount(); i++) {
                if (label.getItemAt(i) == label.getSelectedItem().toString()) {
                    exists = true;
                }
            }
            
            if (!exists) {
               label.addItem(label.getSelectedItem().toString());
               
            }

        }
    }

}
