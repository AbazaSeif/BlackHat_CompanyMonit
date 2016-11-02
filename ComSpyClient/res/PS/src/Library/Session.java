/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import jpcap.JpcapCaptor.*;
import jpcap.packet.*;
import java.util.*;

 
/**
 *
 * @author Administrator
 */
public class Session {
    
    TreeSet<Packet> plist = new TreeSet<Packet>(new PComparator());
    
    
 
}



class PComparator implements Comparator
{

    public int compare(Object o1, Object o2) {
       // throw new UnsupportedOperationException("Not supported yet.");
        TCPPacket p1,p2;
        p1=(TCPPacket)o1;
        p2=(TCPPacket)o2;
        
        if(p1.sequence<p2.sequence)
        {
            return -1;
        }
        else if(p1.sequence>p2.sequence)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
   

   
}