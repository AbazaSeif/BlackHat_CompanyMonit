/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filetransfer;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import compy_client.ComSpy;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abaza
 */
public class server_recevedfile extends Thread {
    
    static String SERVER = "";
    static String FILE_TO_RECEIVED = "";  // you may change this, I give a
    private final static int FILE_SIZE = 6022386; // file size temporary hard coded
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    static boolean Finsed = false;
    
    public void server_recevedfile(String Client_IP, String Download_In) {
        SERVER = Client_IP;
        FILE_TO_RECEIVED = Download_In;
    }
    public boolean isDone(){
        return Finsed;
    }
    @Override
    public void run() {
        try {
            
            sock = new Socket(SERVER, Integer.parseInt(Args.get(Keys.SERVER_PORT)));
            ComSpy.MessageLog("Connecting...");

            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;
            
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);
            
            bos.write(mybytearray, 0, current);
            bos.flush();
            ComSpy.MessageLog("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
        } catch (IOException ex) {
            Finsed = true;
            Logger.getLogger(server_recevedfile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(server_recevedfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ex) {
                    Logger.getLogger(server_recevedfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException ex) {
                    Logger.getLogger(server_recevedfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Finsed = true;
        }
    }
}
