/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author abaza
 */
public class Splash extends JWindow {

    private static final long serialVersionUID = 1L;

    private final int duration;
    showSplash SS = null;
    public Splash(int d) {
        duration = d;
        SS = new showSplash();
        SS.start();
    }

    public class showSplash extends Thread {

        @Override
        public void run() {
            JPanel content = (JPanel) getContentPane();
            content.setBackground(Color.black);

            int width = 500;
            int height = 310;
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width - width) / 2;
            int y = (screen.height - height) / 2;
            setBounds(x, y, width, height);

            Color col = new Color(30, 144, 255);

            JLabel label = new JLabel(new ImageIcon("img/ComSpyLoag.gif"));
            JLabel copyrt = new JLabel("Copyright 2015 - ComSpy by Seif Abaza", JLabel.CENTER);
            copyrt.setFont(new Font("Tahoma", Font.PLAIN, 11));
            copyrt.setForeground(Color.gray);
            content.add(label, BorderLayout.CENTER);
            content.add(copyrt, BorderLayout.SOUTH);

            content.setBorder(BorderFactory.createLineBorder(col, 2));

            setVisible(true);

            try {
                Thread.sleep(duration);
                IsFinshed();
                SS.interrupt();
                SS = null;
            } catch (Exception e) {
                IsFinshed();
                SS.interrupt();
                SS = null;
            }

            setVisible(false);
        }
    }

    public void IsFinshed() {
        MainLoginAdminstration Admin = new MainLoginAdminstration();
        Admin.implimant();
    }
}
