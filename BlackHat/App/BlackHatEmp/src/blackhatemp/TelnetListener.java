/*
 * GNU Library or Lesser Public License (LGPL)
 */
package blackhatemp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Geoffrey
 */
public class TelnetListener extends Thread {

    private final TelnetSession telnetSession;

    private final ServerSocket serverSocket;

    private boolean running = true;

    /**
     * Creates a new instance of TelnetListener
     *
     * @param telnetSession
     * @param serverSocket
     */
    public TelnetListener(TelnetSession telnetSession, ServerSocket serverSocket) {
        super("TelnetListener");
        this.telnetSession = telnetSession;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (running) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (java.security.AccessControlException | IOException exception) {
                telnetSession.stopListener(this);
                return;
            }
            synchronized (this) {
                if (running) {
                    telnetSession.processConnection(socket);
                    // Only after this method can listenFornNewConnection() be called
                    try {
                        wait();
                    } catch (InterruptedException exception) {
                        return;
                    }
                }
            }
        }
    }

    public synchronized void listenForNewConnection() {
        if (running) {
            notify();
        }
    }

    public synchronized void closeListener() {
        if (running) {
            running = false;
            try {
                serverSocket.close();
            } catch (IOException exception) {
            }
            notify();
        }
    }

}
