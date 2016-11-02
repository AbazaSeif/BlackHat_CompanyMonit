/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filetransfer;

import Comman.KeysVer;
import compy_client.ComSpy;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import networksockets.SocketConnect;

/**
 *
 * @author abaza
 */
public class client_sendfile extends Thread {

    static String FILE_TO_SEND = "";

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    boolean Fineshed = false;

    public client_sendfile() {
        SocketConnect SC = new SocketConnect();
        SC.init(KeysVer.Socket_Type.TCP);
        servsock = SC.GetServerSocet();
        Fineshed = false;
    }

    public void SetFile(String filename) {
        FILE_TO_SEND = filename;
        this.start();
    }

    public boolean isDone() {
        return Fineshed;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ComSpy.MessageLog("Waiting...");
                try {
                    sock = servsock.accept();
                    ComSpy.MessageLog("Accepted connection : " + sock);
                    // send file
                    File myFile = new File(FILE_TO_SEND);
                    if (myFile.exists()) {
                        byte[] mybytearray = new byte[(int) myFile.length()];
                        fis = new FileInputStream(myFile);
                        bis = new BufferedInputStream(fis);
                        bis.read(mybytearray, 0, mybytearray.length);
                        os = sock.getOutputStream();
                        ComSpy.MessageLog("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                        os.write(mybytearray, 0, mybytearray.length);
                        os.flush();
                        ComSpy.MessageLog("Done.");
                    }
                } catch (IOException ex) {
                    Fineshed = true;
                } finally {
                    
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException ex) {
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException ex) {
                        }
                    }
                    if (sock != null) {
                        try {
                            sock.close();
                        } catch (IOException ex) {
                        }
                    }
                    Fineshed = true;
                }
            }
        } finally {
            if (servsock != null) {
                Fineshed = true;
                try {
                    servsock.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
