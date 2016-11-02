/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhat;

import Window.MainGUI;
import static blackhat.BlackHat.webcam;
import com.github.sarxos.webcam.Webcam;
import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author abaza
 */
public class Splash extends JWindow {

    showSplash SS = null;
    private final int duration;

    public Splash(int time) {
        this.setName(blackhat.BlackHat.local.getString("titelLoadingBlackHat"));
        try {
            this.setIconImage(ImageIO.read(SecuritySupport.getResourceAsStream("img/blackhatAppIcon.png")));
        } catch (IOException ex) {
        }

        duration = time;
        SS = new showSplash();
        SS.start();
    }

    public class showSplash extends Thread {

        @Override
        public void run() {
            try {
                Webcam.resetDriver();
                webcam = Webcam.getDefault();
                if (webcam != null) {
                    blackhat.BlackHat.Args.put("WEBCAM_MODEL", webcam.getName());
                    webcam.close();
                    webcam = null;
                } else {
                    blackhat.BlackHat.Args.put("WEBCAM_MODEL", blackhat.BlackHat.local.getString("lblNowebcamdetected"));
                    webcam.close();
                    webcam = null;
                }
                JPanel content = (JPanel) getContentPane();
                content.setBackground(Color.black);

                int width = 500;
                int height = 310;
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screen.width - width) / 2;
                int y = (screen.height - height) / 2;
                setBounds(x, y, width, height);

                Color col = new Color(30, 144, 255);
                BufferedImage image = ImageIO.read(SecuritySupport.getResourceAsStream("img/BlackHatLogo1.jpg"));
                JLabel label = new JLabel(new ImageIcon(image));
                JLabel copyrt = null;
                if (blackhat.BlackHat.Args.containsKey("SHOW_COMPANYNAME")) {
                    copyrt = new JLabel(blackhat.BlackHat.Args.get("SHOW_COMPANYNAME"), JLabel.CENTER);
                } else {
                    copyrt = new JLabel(blackhat.BlackHat.local.getString("ApplicationVersion"), JLabel.CENTER);
                }
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
                    JOptionPane.showMessageDialog(blackhat.Splash.this, blackhat.BlackHat.local.getString("msgNoNetwork"), blackhat.BlackHat.local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void IsFinshed() {

            if (Integer.parseInt(blackhat.BlackHat.Args.get("SHOW_LOGIN")) == 1) {
                login LoginBlackHat = new login();
                LoginBlackHat.setVisible(true);
            } else {
                MainGUI MainBlackHat = new MainGUI();
                MainBlackHat.ConnectionLocal();
                MainBlackHat.Scaning();
                MainBlackHat.StartMotion();
                MainBlackHat.setVisible(true);
            }
        }
    }
}
