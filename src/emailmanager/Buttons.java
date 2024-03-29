/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailmanager;

/**
 *
 * @author yousef
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Buttons extends JButton {

    public Buttons() {
        // Is a button with no purpose in life a button at all?
    }

    public Buttons(String text) { // Just text buttons
        // Make all the buttons have the same basic layout
        super(text);
        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        super.setFocusPainted(false);
        super.setOpaque(false);

        super.addMouseListener(new java.awt.event.MouseAdapter() { // Some hover decorations
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(Color.WHITE);
                setBorderPainted(true);
                setContentAreaFilled(true);
                setFocusPainted(true);
                setOpaque(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(UIManager.getColor("control"));
                setBorderPainted(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setOpaque(false);
            }
        });
    }

    public Buttons(String text, String iconRaw, boolean under, String alt) { // fancy button with icon and text positioning
        super(text);
        // get icon into the  correct format
        ImageIcon refreshIconRaw = new ImageIcon(iconRaw);
        Image img = refreshIconRaw.getImage();
        Image IconImg = img.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(IconImg);
        
        // some decoration
        super.setIcon(icon);
        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        super.setFocusPainted(false);
        super.setOpaque(false);
        super.setToolTipText(alt);
        if (under) {
            // Make the text go below the icon
            super.setVerticalTextPosition(SwingConstants.BOTTOM);
            super.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        super.addMouseListener(new java.awt.event.MouseAdapter() { // Some hover decorations
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(Color.WHITE);
                setBorderPainted(true);
                setContentAreaFilled(true);
                setFocusPainted(true);
                setOpaque(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(UIManager.getColor("control"));
                setBorderPainted(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setOpaque(false);
            }
        });

    }
}
