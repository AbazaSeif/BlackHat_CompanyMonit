/*
 * GNU Library or Lesser Public License (LGPL)
 */
package Connection;

import static Connection.TelnetData.ASCII;
import static Connection.TelnetData.NOTHING;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Geoffrey
 */
public class TelnetClient extends TelnetSession {
    
    private TelnetConnection clientConnection;

    /**
     * Creates a new instance of TelnetClient
     *
     * @param telnet
     * @param clientHost
     * @param clientPort
     * @throws java.io.IOException
     */
    public TelnetClient(Telnet telnet, String clientHost, int clientPort) throws IOException {
        super(telnet);
        
        Socket socket = new Socket(clientHost, clientPort);
        clientConnection = new TelnetConnection(this, socket);
        telnet.setSessionState(Telnet.CLIENT);
        telnet.appendStatusMessage("Connection established to " + socket.getInetAddress());
        clientConnection.start();
    }
    
    @Override
    public void receiveData(TelnetConnection telnetConnection, byte[] byteData) {
        TelnetData Data = new TelnetData(byteData, TelnetData.SERVER);
        String Command = Data.getString(ASCII);
        telnet.appendStatusMessage("Reseved : " + Command);
        telnet.appendData(Command);
        
    }
    
    @Override
    public void sendData(int Command) {
        TelnetData data = new TelnetData(TelnetData.ASCII, String.valueOf(Command), NOTHING, TelnetData.CLIENT);
        clientConnection.sendData(data.getData());
        telnet.appendStatusMessage("Send : " + Command);
    }
    
    @Override
    public void sendData(String sendString) {
        TelnetData data = new TelnetData(TelnetData.ASCII, sendString, NOTHING, TelnetData.CLIENT);
        clientConnection.sendData(data.getData());
        telnet.appendStatusMessage("Send : " + sendString);
    }
    
    @Override
    public void stopConnection(TelnetConnection telnetConnection) {
        telnet.stopSession(this);
    }
    
    @Override
    public void stopSession() {
        clientConnection.closeConnection();
        clientConnection = null;
        telnet.appendStatusMessage("Connection closed");
    }
    
}
