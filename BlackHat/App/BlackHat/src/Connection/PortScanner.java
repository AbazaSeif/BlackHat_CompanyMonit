/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Window.MainGUI;

/**
 *
 * @author abaza
 */
public class PortScanner implements Runnable {

    private volatile ScanJobPool scanJobPool = null;
    private final String IP_Address;
    private final String My_IP_Address;
    private final int Port, Thrading, Delay;

    public PortScanner(String IP_RING, int Port, int Threading, int Delay) {
        this.My_IP_Address = IP_RING;
        String tmp = IP_RING.replace(".", ":");
        String[] IP = tmp.split(":");
        IP[3] = "0-255";
        StringBuilder builder = new StringBuilder();
        for (String s : IP) {
            builder.append(s).append(".");
        }
        String NewIP = builder.substring(0, builder.toString().length() - 1);
        this.IP_Address = NewIP;
        this.Delay = Delay;
        this.Port = Port;
        this.Thrading = Threading;
        MainGUI.BasyBox(true);
    }

    public void scanJobPoolFinished() {
        scanJobPool.destroy();
        scanJobPool = null;
        MainGUI.BasyBox(false);
    }

    public void refreshOutput(String outputString) {
        String tmp = outputString.substring(1);
        if (blackhat.BlackHat.Debug) {
            MainGUI.ConnectionListClient.add(tmp);
            if (blackhat.BlackHat.Debug) {
                System.out.println(tmp);
            }
        } else if (!this.My_IP_Address.equalsIgnoreCase(tmp)) {
            MainGUI.ConnectionListClient.add(tmp);
        }
    }

    @Override
    public void run() {
        //TODO: Find way to Search the hostes with other way 
        //        scanJobPool = new ScanJobPool(this, false, "192.168.42.1-255", "2222", 8, 200);
        scanJobPool = new ScanJobPool(this, false, this.IP_Address, String.valueOf(this.Port), this.Thrading, this.Delay);
        scanJobPool.run();
    }
}
