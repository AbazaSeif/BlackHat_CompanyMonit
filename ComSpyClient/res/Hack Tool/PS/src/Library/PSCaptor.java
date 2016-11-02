/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import jpcap.*;
import jpcap.packet.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javamodules.FilterValues;
import javamodules.LogData;
import javax.swing.JTextArea;


/**
 *
 * @author Administrator
 */
public class PSCaptor {
    
    public long refrate = 15000;
    
    
    public static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
    public static NetworkInterface nic;
    public boolean pmode=true;
    public int caplen=65535;
    public static JpcapCaptor captor;
    //testing
    public static int CC=0;
   
    public static int buffsize=1000;
    public static int counter=0;
    public Thread t1,t2,t3;
    public DefaultTableModel dtbm1;
    public JTextArea ja;
    public SessionBuf sb = new SessionBuf();
    public BlockingQueue<Packet> pque = new LinkedBlockingQueue<Packet>(1000);
    
    
    public TreeSet<String> ws = new TreeSet<String>();
    public TreeSet<Integer> ps = new TreeSet<Integer>(new PortComparator());
    public TreeSet<InetAddress> is = new TreeSet<InetAddress>();
    public int status;
    
    
    
    public PSCaptor()
    {
        try {
            nic=devices[0];
            captor=JpcapCaptor.openDevice(nic, caplen, pmode, caplen);
            captor.setFilter("ip.addr==192.168.120.77", true);
        } catch (IOException ex) {
            System.out.println("aaa");
        }
        
    }      
    
    public PSCaptor(int devno,boolean mode,int bsize,DefaultTableModel d1,JTextArea j)
    {
        try {
            buffsize=bsize;
            nic=devices[devno];
            pmode=mode;
            captor=JpcapCaptor.openDevice(nic, caplen, pmode, caplen);
            dtbm1=d1;
            ja =j;
     //       initFilter();
            
            LogData.createLog();
        } catch (IOException ex) {
            Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      
    //method for getting device list
    public static String[] getdevlist()
    {
     
        
        String[] devstr = new String[5];
                 
                for (int i = 0; i < devices.length; i++) 
                {
  
  
                               for (NetworkInterfaceAddress a : devices[i].addresses)
                                devstr[i] = "|"+a.address + "|-->"  + devices[i].description;
               
                }
                
                return devstr;
    }
    
    public void wrlog(Packet p)
    {
        String[] s = new String[4];
        EthernetPacket ep = (EthernetPacket)p.datalink;
        s[2] = ep.getSourceAddress();
        s[3] = ep.getDestinationAddress();
        
        TCPPacket tp = (TCPPacket)p;
        s[0] = tp.src_ip.toString();
        s[1] = tp.dst_ip.toString();
        
        LogData.writeLog(s[0], s[1], tp.src_port, tp.dst_port, s[2], s[3], new Date(p.sec*1000));
        
        
    }
    
   public void initFilter()
    {
        FilterValues.readIPData();
        FilterValues.readPortData();
        FilterValues.readWordData();
        
        if(FilterValues.ip != null)
           is.addAll(FilterValues.ip);
        
        if(FilterValues.ports != null)
            ps.addAll(FilterValues.ports);

        if(FilterValues.words != null)
            ws.addAll(FilterValues.words);
        
        status = 7 - FilterValues.getFilterStatus();
        
        System.out.println(is.toString());
    }
    
    
    public boolean isPassed(Packet p)
    {
        switch(status)
        {
            case 0:
                if(portPassed(p) || ipPassed(p) || wordPassed(p))
                    return true;
                else 
                    return false;
                
            case 1:
                 if(portPassed(p) || ipPassed(p) || !wordPassed(p))
                    return true;
                 else 
                    return false;
                
            case 2:
                 if(portPassed(p) || !ipPassed(p) || wordPassed(p))
                    return true;
                 else 
                    return false;  
                
             case 3:
             if(portPassed(p) || !ipPassed(p) || !wordPassed(p))
                return true;
             else 
                return false;   
                 
              case 4:
                 if(!portPassed(p) || ipPassed(p) || wordPassed(p))
                    return true;
                 else 
                    return false;       
                  
                case 5:
                 if(!portPassed(p) || ipPassed(p) || !wordPassed(p))
                    return true;
                 else 
                    return false;  
                    
                 case 6:
                 if(!portPassed(p) || !ipPassed(p) || wordPassed(p))
                    return true;
                 else 
                    return false;   
                     
                 case 7:
                 if(!portPassed(p) || !ipPassed(p) || !wordPassed(p))
                    return true;
                 else 
                    return false;   

                 default:
                       return true;
        }
        
    }
    
    
    public boolean portPassed(Packet p)
    {
        if(FilterValues.ports != null)
        {
                if( ps.contains(((TCPPacket)p).src_port) || ps.contains(((TCPPacket)p).dst_port)  )
                    return true;
                else 
                    return false;
        }
        else
            return true;
    }
    
    public boolean ipPassed(Packet p)
    {
        
        
        if(FilterValues.ip != null)
        {
         //   System.out.println("not null");
            
                if( is.contains(((IPPacket)p).src_ip) || is.contains(((IPPacket)p).dst_ip)  )
                {
                //    System.out.println("abcd");
                    return true;
                }
                else 
                    return false;
        }
        else
            return true;
    }
    
  
    public boolean wordPassed(Packet p)
    {
        if(FilterValues.words != null)
        {
                    String s = "error";
                    try {
                        s = new String(p.data,"UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for(String ss: ws)
                    {
                        if(s.contains(ss))
                            return true;

                    }
                        return false;
        }
        else
            return true;
    }
 //----------------packet view thread-------------------------------------------------   
    
    java.lang.Runnable qtask = new Runnable() {

        @Override
        public void run() {
            
            while(true)
            {
                try {
                    Packet tp = pque.take();
                    wrlog(tp);
                    sb.addpacket(tp);
       
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
            }
            
        }
    };
    
    
    
    
    
     java.lang.Runnable ctask=new Runnable(){
        @Override
            public void run(){
                captor.loopPacket(-1, new PacketHandler());
            }
         };
    
     
     
    private void startCapThread()
    {	  
            t1 =  new Thread(ctask);
            t1.setName("capstart");
            t1.setPriority(Thread.MAX_PRIORITY);
            t1.start();
            
            
            t3 = new Thread(qtask);
            t3.setName("consumer");
            t3.start();
            
                  initFilter();

            startSesThread();
            
            
    }  
    private void stopCapThread ()
    {
                t1.stop();
                t3.stop();
                
                stopSesThread();
    }
 
    
     //AL on START BUTTON with DTM (MENU also)
    public void startUpdating()
    {
       
        startCapThread();
        
        startSesThread();
        
    }
    //AL on STOP BUTTON
    public void stopUpdating()
    {
        stopCapThread();
    }
    
 //-------------------session view updating---------------------------
    
    
    //AL on SESSION BUTTON/IMAGE
    public void startSesUpdating()
    {
        
      //  startSesThread();
        
    }
    
    //AL on PACKET BUTTON 
    public void stopSesUpdating()
    {
        stopSesThread();
    }
    
    public void startSesThread()
    {
        t2 =  new Thread(stask);
            t2.setName("sesstart");
            t2.start();
    }
    
    
    public void stopSesThread()
    {
        t2.stop();
    }
    
    java.lang.Runnable stask = new Runnable() {

        @Override
        public void run() {
            
            while(true)
            {
                        try {
                            Thread.sleep(refrate);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                      sb.buildSession(ja);
          
            }
          
        }
    };
    
//----------------------------------------------------------------------    
class PacketHandler implements PacketReceiver{
        


    @Override
    public void receivePacket(Packet packet) {
        
        
     //  if(!isPassed(packet)) return;
             
        //add for packet view        
        if(counter<=buffsize)
        {
         addrow(packet);
         counter++;
         
        }
        
       //  System.out.println("a");
                       
                      //add packet into hashmap for gosession 
         
                   if(packet instanceof TCPPacket &&
                               (((TCPPacket)packet).src_port==80 || ((TCPPacket)packet).dst_port==80))
                   {  
                try {
                    //  System.out.println(PSCaptor.CC);

                    
                   /*    PSCaptor.CC++;
                       
                       if(PSCaptor.CC>=200 && PSCaptor.CC<=300) 
                       { sb.buildSession(ja);
                       PSCaptor.CC= 400;
                       System.out.println("session start");
                       }
                       
                       
                       
                    **/
                        
                   
                       pque.put(packet);
                       
                    //   sb.addpacket(packet);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
                }


                   }
           
         //add packet into queue for log
                                   
    }
    
       
    public void gosession(Packet p)
    {
        
    }
    
   
    
        public void addrow(Packet packet)
        {  
          String[] s = new String[12];  

            //disp method to add into default table model


                        s[0] = String.valueOf(counter);                                     //no
                        s[1] = new Date(packet.sec*1000).toGMTString();                          //time
                        s[10] = String.valueOf((int)(packet.len));                           //size



                                try {

                                    s[11] = new String(packet.data,"UTF-8");
                                } catch (UnsupportedEncodingException ex) {
                                    s[11] = "--------------Unsupported Char Set --------------------";
                                }



                  //      System.out.println(s[11]);
    //                     try {
    //                    s[11] = new String(packet.data,"ISO-8859-1");
    //                } catch (UnsupportedEncodingException ex) {
    //                    Logger.getLogger(PSCaptor.class.getName()).log(Level.SEVERE, null, ex);
    //                }



                        DatalinkPacket datalink  = packet.datalink;
                           //always true
                         if(datalink instanceof jpcap.packet.EthernetPacket)
                         {
                             EthernetPacket ep = (EthernetPacket)datalink;

                              s[2] = ep.getSourceAddress();                     //SEA
                              s[3] = ep.getDestinationAddress();                //DEA
                              switch(ep.frametype)                              //FRAME
                              {
                                  case EthernetPacket.ETHERTYPE_ARP:
                                      s[4]= "ARP"; break;
                                  case EthernetPacket.ETHERTYPE_IP:
                                      s[4]= "IP";  break;
                                  case EthernetPacket.ETHERTYPE_LOOPBACK:
                                      s[4]= "LOOPBACK";  break;
                                  case EthernetPacket.ETHERTYPE_REVARP:
                                      s[4]= "Reverse ARP";  break;
                                  default:
                                      s[4]= String.valueOf(ep.frametype);
                               }
                         }	 





                    if(packet instanceof jpcap.packet.TCPPacket)
                    {
                                            TCPPacket p =(TCPPacket)packet;
                                          //TCP

                                            s[5] = "TCP/IP";                            //protocol
                                            s[6] = p.src_ip.toString();                 //SIP
                                            s[7] = p.dst_ip.toString();                 //DIP
                                            s[8] = String.valueOf(p.src_port);          //SP
                                            s[9] = String.valueOf(p.dst_port);          //DP

                    }

                                    //UDP
                           else if(packet instanceof jpcap.packet.UDPPacket)
                           {
                                            UDPPacket p=(UDPPacket)packet;
                                            s[5] = "UDP/IP";
                                            s[6] = p.src_ip.toString();
                                            s[7] = p.dst_ip.toString();
                                            s[8] = String.valueOf(p.src_port);
                                            s[9] = String.valueOf(p.dst_port);   
                           }
                                    //ping, ICMPPacket
                           else if(packet instanceof jpcap.packet.ICMPPacket)
                           {
                               ICMPPacket p=(ICMPPacket)packet;
                               //ICMP
                                            s[5] = "ICMP/IP";
                                            s[6] = p.src_ip.toString();
                                            s[7] = p.dst_ip.toString();
                                            s[8] = "--";
                                            s[9] = "--";
                                    }

                           else if(packet instanceof jpcap.packet.ARPPacket)
                           {
                               ARPPacket p=(ARPPacket)packet;
                               //ARP

                                            s[5] = "ARP";
                                            s[6] = "--";
                                            s[7] = "--";
                                            s[8] = "--";
                                            s[9] = "--";
                            }


                   dtbm1.addRow(s);
                   
                   //testing
//                   if(packet instanceof TCPPacket &&
//                               (((TCPPacket)packet).src_port==80 || ((TCPPacket)packet).dst_port==80))
//                   {  
//                   LogData.writeLog(s[6], s[7], Integer.parseInt(s[8]), Integer.parseInt(s[9]), s[2], s[3], new Date(packet.sec));
//                   }
                   //testing
   //            System.out.println(packet.toString());
    //               if(packet instanceof TCPPacket &&
    //		   (((TCPPacket)packet).src_port==80 || ((TCPPacket)packet).dst_port==80))
    //               {
    //               
                    System.out.println(s[8] + "  " +s[9]);
    //               }



        }   //eof add row;

    
}









   //Testing of File 
    public static void main(String[] args)
    {
        PSCaptor psc = new PSCaptor(2, true, 100000,new DefaultTableModel(),new JTextArea());
        SessionBuf sb = new SessionBuf();
        String[] a = PSCaptor.getdevlist();
        for(String b: a)
        {
            System.out.println(b);
        }
        
            
       //   psc.startUpdating();
      // psc.initFilter();
        
        System.out.println(psc.ps.contains(new Integer(80)));
      
    }
    
   
   class PortComparator implements Comparator
{

        @Override
        public int compare(Object o1, Object o2) {
            int i1,i2;
            i1 = ((Integer)o1).intValue();
            i2 = ((Integer)o2).intValue();
            
            if(i1<i2) return -1;
            else if(i1>i2) return 1;
            else return 0;
        }
       
   } 
    
    
    
     
    
}
