/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JPanel;

/**
 *
 * @author abaza
 */
public class Server_Screen{

    private static Socket cSocket = null;
    private static DataOutputStream psswrchk = null;
    private static DataInputStream verification = null;
    private static String verify = "", width = "", height = "";
    CreateFrame createFrame = null;

    public boolean init(String IP, int Port, String Password , JPanel Panel) throws IOException {
        cSocket = new Socket(IP, Port);

        psswrchk = new DataOutputStream(cSocket.getOutputStream());
        verification = new DataInputStream(cSocket.getInputStream());
        width = String.valueOf(Panel.getWidth());
        height = String.valueOf(Panel.getHeight());
        Panel.setSize(Integer.parseInt(width), Integer.parseInt(height));
        psswrchk.writeUTF(Password);
        verify = verification.readUTF();

        if (verify.equals("valid")) {
            width = verification.readUTF();
            height = verification.readUTF();
            return true;
        } else {
            return false;
        }
    }

    public void StartRecevedScreen(JPanel Panel) {
        createFrame = new CreateFrame(Server_Screen.cSocket, width, height, Panel);
        createFrame.setName("StartScreen");
    }

    public void StopRecevedScreen() {
        if (createFrame != null) {
            createFrame.interrupt();
            createFrame = null;
            Server_Screen.cSocket = null;
            Server_Screen.psswrchk = null;
            Server_Screen.verification = null;
        }
    }

}
