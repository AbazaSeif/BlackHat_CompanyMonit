/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author abaza
 */
class ReceiveScreen extends Thread {

    private final ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    private final boolean continueLoop = true;
    InputStream oin = null;
    Image image1 = null;

    public ReceiveScreen(InputStream in, JPanel Panel) {
        oin = in;
        cPanel = Panel;
        start();
    }

    public void UpdatePanel(JPanel Panel) {
        cPanel = Panel;
    }

    @Override
    public void run() {
        try {
            while (continueLoop) {
                byte[] bytes = new byte[1024 * 1024];
                int count = 0;
                do {
                    count += oin.read(bytes, count, bytes.length - count);
                } while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));
                image1 = ImageIO.read(new ByteArrayInputStream(bytes));
                image1 = image1.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_DEFAULT);//SCALE_FAST
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(image1, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        } catch (IOException | NullPointerException e) {
            image1 = null;
            cPanel.removeAll();
            cPanel.updateUI();
            
        }
    }

}
