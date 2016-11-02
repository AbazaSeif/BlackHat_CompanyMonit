/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import com.google.common.collect.*;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import jpcap.packet.*;

/**
 *
 * @author Administrator
 */
public class SessionBuf {
    
    Multimap<CSocket,Packet> myMap = HashMultimap.create();
    public static int Limit = 10;
    
 
    
     Collection<CSocket> cs;
     public JTextArea jaa;
    
    public synchronized void addpacket(Packet p)
    {

    if(getSessionKeys().size()<= SessionBuf.Limit)  
    {
        myMap.put(new CSocket(p), p);
           
    }   

    }
            
    public synchronized void buildSession(JTextArea j)
    {
        jaa =j;
        jaa.removeAll();
        cs = getSessionKeys();
 
        for(CSocket tcs : cs)
        {
            String sr ,cl;
            Collection<Packet> c = getpacketsbyCS(tcs);
            TreeSet<Packet> plist = new TreeSet<Packet>(new PComparator());
            plist.addAll(c);
            
            tcs.buff = new StringBuilder("*");
            
                  for(Packet p : plist)
                    {
                        try {
                            tcs.buff.append(new String(p.data,"UTF-8"));

                           //testing
                          //  System.out.println(new String(p.data,"UTF-8"));

                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(SessionBuf.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
  
//            cl = tcs.clip.getCanonicalHostName();
//            sr = tcs.srip.getCanonicalHostName();
            
//            tcs.buff.append("client:"+cl+"\nserver:"+sr);
            
            jaa.append(tcs.toString());
            
  
            
            //Testing
            
           //System.out.println(tcs.buff.toString());
            jaa.append("-----------------------------------------------------------------------------------------------------");
            
        }
          
    }
    
        public Collection<CSocket> getSessionKeys()
        {
            return myMap.keySet();
        }
            
        
        public Collection<Packet> getpacketsbyCS(CSocket cs1)
        {
                 return myMap.get(cs1);           
        }
        
        
        
        public void printses(Packet p)
        {
            
                  
                      
                          
        }
        
}



/*
class sesData
{
    
    
    
    public sesData()
    {
        
    }
    
    
}
 
 **/
 