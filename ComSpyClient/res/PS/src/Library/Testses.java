/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

/**
 *
 * @author Administrator
 */
public class Testses {
    JpcapCaptor captor;
    SessionBuf sb;
    
    public Testses(){
        try {
            captor=JpcapCaptor.openFile("D:\\aaaa.pcap");
        } catch (IOException ex) {
            Logger.getLogger(Testses.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb = new SessionBuf();
        
    }
    
    public void loadp()
    {
       
        
        
        while(true){
             //read a packet from the opened file
         Packet packet=captor.getPacket();
             //if some error occurred or EOF has reached, break the loop
         if(packet==null || packet==Packet.EOF) break;
             //otherwise, print out the packet
         if(packet instanceof TCPPacket &&(((TCPPacket)packet).src_port==80 || ((TCPPacket)packet).dst_port==80))
         
         {      
             sb.addpacket(packet);
         }
        
        }

        
    }
    
    
    public void testprint(){
        
        System.out.println(sb.getSessionKeys());
        
       Collection<CSocket> cs = sb.getSessionKeys();
        
        for(CSocket tcs : cs)
        {
            System.out.println(tcs.toString());
            Collection<Packet> c = sb.getpacketsbyCS(tcs);
            TreeSet<Packet> plist = new TreeSet<Packet>(new PComparator());
            plist.addAll(c);
            
            for(Packet p : plist)
            {
                System.out.println(((TCPPacket)p).sequence);
            }
            
        
         }
    }   
    public void dispses()
    {
        sb.buildSession(new JTextArea());
    }
    
    public static void main(String[] a)
    {
        Testses ts = new Testses();
            ts.loadp();
         //   ts.testprint();
           ts.dispses();
    }
}
