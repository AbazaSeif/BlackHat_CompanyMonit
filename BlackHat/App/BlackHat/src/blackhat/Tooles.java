/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhat;

import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author abaza
 */
public class Tooles {

    public void ShowImage(String Path, JLabel image, int Width, int Height) {
        try {
            BufferedImage originalImage = ImageIO.read(SecuritySupport.getResourceAsStream(Path));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resizeImage(originalImage, type, Width, Height);
            image.setIcon(new ImageIcon(resizeImageJpg));
            image.updateUI();
        } catch (IOException ex) {
            Logger.getLogger(Tooles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ShowImage(String Path, JXImageView image, int Width, int Height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(Path));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resizeImage(originalImage, type, Width, Height);
            image.setImage(resizeImageJpg);
            image.setBackground(Color.black);
            image.updateUI();
        } catch (IOException ex) {
            Logger.getLogger(Tooles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int Width, int Height) {
        BufferedImage resizedImage = new BufferedImage(Width, Height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, Width, Height, null);
        g.dispose();
        return resizedImage;
    }

    public void copyFile(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }
}
