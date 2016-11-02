/*
 * GNU Library or Lesser Public License (LGPL)
 */

package blackhatemp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Geoffrey
 */
public class TelnetConnection extends Thread {

    public static final int BUFFER_SIZE = 1024;

    private final TelnetSession telnetSession;

    private final Socket socket;
    private final BufferedInputStream inputStream;
    private final BufferedOutputStream outputStream;

    private boolean running = true;

    /**
     * Creates a new instance of TelnetConnection
     * @param telnetSession
     * @param socket
     * @throws java.io.IOException
     */
    public TelnetConnection(TelnetSession telnetSession, Socket socket) throws IOException {
        super("TelnetConnection");
        this.telnetSession = telnetSession;
        this.socket = socket;
        inputStream = new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE);
        outputStream = new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE);
    }

    @Override
    public void run() {
        byte[] receivedData = new byte[BUFFER_SIZE];
        while (running) {
            int number = -1;
            try {
                number = inputStream.read(receivedData);
            } catch (IOException exception) {
            }
            if (number <= 0) {
                telnetSession.stopConnection(this);
            } else {
                byte[] data = new byte[number];
                System.arraycopy(receivedData, 0, data, 0, number);
                telnetSession.receiveData(this, data);
            }

        }
    }

    public void sendData(byte[] byteData) {
        try {
            outputStream.write(byteData);
            outputStream.flush();
        } catch (IOException exception) {
            telnetSession.stopConnection(this);
        }
    }

    public synchronized void closeConnection() {
        if (running) {
            running = false;
            try {
                socket.close();
            } catch (IOException exception) {
            }
        }
    }

}
