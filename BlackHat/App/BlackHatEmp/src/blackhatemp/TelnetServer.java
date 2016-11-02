/*
 * GNU Library or Lesser Public License (LGPL)
 */
package blackhatemp;

import static blackhatemp.TelnetData.ASCII;
import static blackhatemp.TelnetData.NOTHING;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Geoffrey
 */
public class TelnetServer extends TelnetSession {

    private final TelnetListener serverListener;
    private TelnetConnection serverConnection = null;
    protected String AddressMe = null;

    /**
     * Creates a new instance of TelnetServer
     *
     * @param telnet
     * @param serverPort
     * @throws java.io.IOException
     */
    public TelnetServer(Telnet telnet, int serverPort) throws IOException {
        super(telnet);
        ServerSocket serverSocket = new ServerSocket(serverPort);
        serverListener = new TelnetListener(this, serverSocket);
//        serverListener.setPriority(4);
        serverListener.start();
    }

    @Override
    public String MyAddress() {
        return this.AddressMe;
    }

    @Override
    public void processConnection(Socket socket) {
        try {
            try {
                serverConnection = new TelnetConnection(this, socket);
            } catch (IOException exception) {
                serverListener.listenForNewConnection();
                return;
            }
            serverConnection.start();
            InetAddress tempAddress = socket.getInetAddress();
            NetworkInterface network;
            network = NetworkInterface.getByInetAddress(tempAddress);
            if (network != null) {
                byte[] mac = network.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                if (blackhatemp.BlackHatEmp.Debug) {
                    System.out.println("Current IP address : " + tempAddress.getHostAddress());
                    System.out.print("Current MAC address : ");
                    System.out.println(sb.toString());
                }

                this.AddressMe = tempAddress.getHostAddress() + ":" + sb.toString();
            }
            Telnet.ServerIP = socket.getInetAddress().toString().substring(1, socket.getInetAddress().toString().length());
            if (blackhatemp.BlackHatEmp.Debug) {
                telnet.message("Connection established from " + socket.getInetAddress() + " Status : " + Telnet.SERVER_CONNECTED);
            }
        } catch (SocketException ex) {
            Logger.getLogger(TelnetServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void receiveData(TelnetConnection telnetConnection, byte[] byteData) {
        TelnetData Data = new TelnetData(byteData, TelnetData.CLIENT);
        String Command = Data.getString(ASCII);

        telnet.appendData(Command);
        telnet.message("Reseved : " + Command);
    }

    @Override
    public void sendData(String sendString) {
        if (serverConnection != null) {
            TelnetData data = new TelnetData(TelnetData.ASCII, sendString, NOTHING, TelnetData.SERVER);
            serverConnection.sendData(data.getData());
            telnet.message("SEND : " + sendString);
        }
    }

    @Override
    public void sendData(int Command) {
        if (serverConnection != null) {
            TelnetData data = new TelnetData(TelnetData.ASCII, String.valueOf(Command) + ":" + blackhatemp.BlackHatEmp.UUID + ":" + blackhatemp.BlackHatEmp.PASSWORD, NOTHING, TelnetData.SERVER);
            serverConnection.sendData(data.getData());
            telnet.message("SEND : " + Command);
        }
    }

    private synchronized void closeConnection() {
        if (serverConnection != null) {
            serverConnection.closeConnection();
            serverConnection = null;
            telnet.message("Connection closed");
        }
    }

    @Override
    public void stopConnection(TelnetConnection telnetConnection) {
        closeConnection();
        telnet.message("Listening... STATUS : " + Telnet.SERVER_LISTENING);
        serverListener.listenForNewConnection();
    }

    @Override
    public void stopListener(TelnetListener telnetListener) {
        telnet.message("Stop Listener");
        telnet.stopSession(this);
    }

    @Override
    public void stopSession() {
        closeConnection();
        serverListener.closeListener();
        telnet.message("Listening stopped");
    }

}
