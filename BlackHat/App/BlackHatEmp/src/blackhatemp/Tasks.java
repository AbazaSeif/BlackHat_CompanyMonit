/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhatemp;

/**
 *
 * @author abaza
 */
public class Tasks implements Runnable {

    Telnet TNet = null;

    public Tasks() {
        TNet = new Telnet();
    }

    @Override
    public void run() {
        TNet.createServer(3369);
    }

}
