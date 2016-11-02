/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

/**
 *
 * @author abaza
 */
public class client_command_implimantation {

    
    client_command Client = null;

    public void Switching(boolean SwitchKey) {
        if (SwitchKey) {
            System.out.println("Open Shell");
            Client = new client_command();
            Client.start();
            Client.run();
        } else {
            System.out.println("Close Shell");
            Client.End();
            Client = null;
        }
    }
}
