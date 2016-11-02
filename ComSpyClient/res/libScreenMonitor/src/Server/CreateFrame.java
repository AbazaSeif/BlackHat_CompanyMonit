/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JPanel;

/**
 *
 * @author abaza
 */
class CreateFrame extends Thread{
    String width="", height="";
    private Socket cSocket = null;
    private final JPanel Panel;
    public CreateFrame(Socket cSocket, String width, String height, JPanel interFrame) {
        this.width = width;
        this.height = height;
        this.cSocket = cSocket;
        this.Panel = interFrame;
        start();
    }

    @Override
    public void run(){
        InputStream in = null;
        try{
            in = cSocket.getInputStream();
        }catch(IOException e){
        }
        new ReceiveScreen(in,Panel);
        new SendEvents(cSocket,Panel,width,height);
    }
}
