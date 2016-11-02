/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author abaza
 */
public class server_shell {

    public String Send_Shell(String Client_IP, String Command) {
        try {
            Socket client = new Socket(Client_IP, Integer.parseInt(Args.get(Keys.SERVER_PORT)));
            BufferedInputStream in;
            StringBuilder sb;
            try (BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream())) {
                in = new BufferedInputStream(client.getInputStream());
                Command += "\n";
                byte[] cmd = Command.getBytes();
                out.write(cmd, 0, cmd.length);
                out.flush();
                sb = new StringBuilder();
                int c = -1;
                while ((c = in.read()) != -1) {
                    sb.append((char) c);
                }
            }
            in.close();
            return sb.toString();
        } catch (NumberFormatException | IOException e) {
        }
        return null;
    }

}
