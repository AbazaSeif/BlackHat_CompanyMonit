/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;
import com.google.common.collect.*;

import java.net.*;
import java.util.Date;
import jpcap.packet.*;




/**
 *
 * @author Administrator
 */
public class CSocket {
    
   public InetAddress clip,srip;
   public int clport,srport;
   public StringBuilder buff = new StringBuilder();
   public String status,chost,shost;
   public Date sdate,edate;
//   public boolean mapdone = false;
   
   public CSocket(InetAddress ci,int cp,InetAddress si,int sp)
   {
       clip=ci;
       srip=si;
       clport=cp;
       srport=sp;
       buff = new StringBuilder(1000);
       
   }

    public CSocket(Packet p)
    {
       TCPPacket tcp =(TCPPacket)p;
       
       clip = tcp.src_ip;
       srip = tcp.dst_ip;
       clport = tcp.src_port;
       srport = tcp.dst_port;
       
           if(tcp.syn)
           {
               status="Established";
               sdate = new Date(tcp.sec);
           }
           if(tcp.fin)
           {
               status="Complted";
               
           }
           if(tcp.rst)
           {
               status="Aborted";
               edate = new Date(tcp.sec);
           }
    }
    
    //hashcode equals need to implement
    
    @Override
    public boolean equals(Object o)
    {
        CSocket cs = (CSocket)o;
        
        if(clport==cs.clport && srport==cs.srport && clip.equals(cs.clip) && srip.equals(cs.srip) )
            return true;
        else 
            return false;
    }
    
    @Override
    public int hashCode()
    {
        return (clport + srport);
    }
    
    
    public String[] transformString()
    {
        String[] s = new String[6];
        s[0] = clip.toString();
        s[1] = srip.toString();
        s[2] = String.valueOf(clport);
        s[3] = String.valueOf(srport);
        s[4] = status;
        s[5] = buff.toString();
        
        return s;
    }
    
    @Override
    public String toString()
    {
        String[] ss;
        String sl;
        ss = transformString();
        sl = "\nClient Ip :" + ss[0] 
                +"\nServer IP :" + ss[1]
                +"\nClient Port :" + ss[2]
                +"\nServer Port :" + ss[3]
                +"\nStatus" + ss[4] 
                +"\n DATA:" + ss[5] ;
        return sl;
        
    }
    
    
    
    
}
