/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksockets;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.KeysVer.Socket_Type;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abaza
 */
public class SocketConnect {

    DatagramSocket UDPSocet = null;
    ServerSocket TCPSocket = null;

    public boolean init(Socket_Type ST) {
        try {
            switch (ST.getValue()) {
                case 1: {
                    if (TCPSocket == null) {
                        TCPSocket = new ServerSocket(Integer.parseInt(Args.get(Keys.SERVER_PORT)));
                        return (!TCPSocket.isClosed());
                    } else {
                        TCPSocket.close();
                        TCPSocket = null;
                        TCPSocket = new ServerSocket(Integer.parseInt(Args.get(Keys.SERVER_PORT)));
                        return (!TCPSocket.isClosed());
                    }
                }
                case 2: {
                    if (UDPSocet == null) {
                        UDPSocet = new DatagramSocket(Integer.parseInt(Args.get(Keys.SERVER_PORT)));
                        return UDPSocet.isConnected();
                    } else {
                        UDPSocet.close();
                        UDPSocet = null;
                        UDPSocet = new DatagramSocket(Integer.parseInt(Args.get(Keys.SERVER_PORT)));
                        return UDPSocet.isConnected();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ServerSocket GetServerSocet() {
        return this.TCPSocket;
    }

    public DatagramSocket GetDatagramSocket() {
        return this.UDPSocet;
    }
}
