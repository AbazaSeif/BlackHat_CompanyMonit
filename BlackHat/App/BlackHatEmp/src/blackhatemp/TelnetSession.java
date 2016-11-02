/*
 * GNU Library or Lesser Public License (LGPL)
 */
package blackhatemp;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Geoffrey
 */
public abstract class TelnetSession {

    protected Telnet telnet;

    /**
     * Creates a new instance of TelnetSession
     *
     * @param telnet
     * @throws java.io.IOException
     */
    public TelnetSession(Telnet telnet) throws IOException {
        this.telnet = telnet;
    }

    public void processConnection(Socket socket) {

    }

    public void receiveData(TelnetConnection telnetConnection, byte[] byteData) {

    }

    public void sendData(String sendString) {

    }

    public void sendData(int Command) {

    }

    public void stopConnection(TelnetConnection telnetConnection) {

    }

    public void stopListener(TelnetListener telnetListener) {

    }

    public void stopSession() {

    }

    public String MyAddress() {
        return null;
    }
;

}
