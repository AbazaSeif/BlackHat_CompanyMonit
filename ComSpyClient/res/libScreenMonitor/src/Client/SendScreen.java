/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author abaza
 */
class SendScreen extends Thread {

    Socket socket = null;
    Robot robot = null;
    Rectangle rectangle = null;
    boolean continueLoop = true;
    OutputStream oos = null;

    public SendScreen(Socket sc, Robot robot, Rectangle rectangle) {
        this.socket = sc;
        this.robot = robot;
        this.rectangle = rectangle;
        start();
    }

    @Override
    public void run() {
        try {
            oos = socket.getOutputStream();
            while (continueLoop) {
                BufferedImage image = robot.createScreenCapture(rectangle);

                ImageIO.write(image, "JPEG", oos);
                oos.flush();
                Thread.sleep(5);
            }
        } catch (IOException | InterruptedException ex) {
        } finally {
            oos = null;
        }
    }
}
