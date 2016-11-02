package Client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author seif abaza <yagan.seif@yandex.ru>
 */
public class Client_Screen extends Thread {

    ServerSocket socket = null;
    DataInputStream password = null;
    DataOutputStream verify = null;
    SendScreen SendScreen = null;
    ReceiveEvents ReceiveEvents = null;
    private static boolean Loop = false;
    private String Password = null, width = "", height = "";
    Robot robot = null;
    Rectangle rectangle = null;

    public void init(String Password, int Port ) throws IOException, AWTException {
        this.Password = Password;
        socket = new ServerSocket(Port);

        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        width = String.valueOf(dim.getWidth());
        height = String.valueOf(dim.getHeight());
        
        rectangle = new Rectangle(dim);
        robot = new Robot(gDev);
        Loop = true;
        start();
    }

    @Override
    public void run() {
        while (Loop) {
            try {
                Socket sc = socket.accept();
                password = new DataInputStream(sc.getInputStream());
                verify = new DataOutputStream(sc.getOutputStream());

                String Pass = password.readUTF();
                
                if (Pass.equals(Password)) {
                    verify.writeUTF("valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);

                    SendScreen = new SendScreen(sc, robot, rectangle);
                    ReceiveEvents = new ReceiveEvents(sc, robot);
                } else {
                    verify.writeUTF("Invalid");
                }
            } catch (IOException ex) {
            }
        }
    }

    public void StopSendScreen() {
        if (Loop) {
            Loop = false;
            socket = null;
            password = null;
            verify = null;
            if (SendScreen != null) {
                SendScreen.interrupt();
                SendScreen = null;
            }
            if (ReceiveEvents != null) {
                ReceiveEvents.interrupt();
                ReceiveEvents = null;
            }
        }
    }

}
