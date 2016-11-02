package edu.huji.cs.netutils.capture.analyze;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.bcel.internal.generic.ISUB;

import edu.huji.cs.netutils.NetUtilsException;
import edu.huji.cs.netutils.parse.FiveTuple;
import edu.huji.cs.netutils.parse.PacketSide;
import edu.huji.cs.netutils.parse.TCPPacket;

/**
 * 
 * 
 * 
 * @author roni bar-yanai
 *
 */
public class FlowHttp 
{
	private FlowTCP myFlow;
	
	public static boolean isHttp(FlowTCP flow)
	{
		// regular expression pattern for HTTP.
        Pattern p = Pattern.compile("(GET|POST|HEAD|POST|DELETE|TRACE|CONNECT).*HTTP");
        
        if (/*flow.isEstablished() && */flow.isPayload())
        {
                int n = flow.getFirstPayloadPacketNum();
                byte data[] = flow.getTCPPkt(n).getTCPData();
               
                // try to match
                Matcher m = p.matcher(new String(data));
                if (m.find())
                {
                    return true;
                }      
        }
        
        return false;
	}
	
	public static boolean isMultipleGetHttp(FlowTCP flow)
	{
		// regular expression pattern for HTTP.
        Pattern p = Pattern.compile("(GET|POST|HEAD|POST|DELETE|TRACE|CONNECT).*HTTP");
        
        TCPPacket pkts[] = flow.getReasmble(PacketSide.CLIENT_TO_SERVER);
        int n = 0;
        for(int i = 1 ; i<pkts.length ; i++)
        {
            byte data[] = pkts[i].getTCPData();
           // System.out.println(new String(data));
            // try to match
            Matcher m = p.matcher(new String(data));
            if (m.find())
            {
                n++;
            }
        	
        }
              
        System.out.println("Total Response "+n);
        
        return n>1;
	}
	
	public static void main(String[] args) throws IOException, NetUtilsException 
	{
		
		CaptureFileFlowAnalyzer an = new CaptureFileFlowAnalyzer("/home/rbaryanai/captures/netflix.cap");
		int n = 0;
		 for (Flow f : an)
	     {
	           if (f.getFlowType() == PacketType.TCP)
	           {
	        	   n++;
	        	   FlowTCP ftcp = (FlowTCP) f;
	        	   
	        	   if(isHttp(ftcp))
	        	   {
	        		   System.out.println("HTTP FLOW");
	        		   String str = new String(ftcp.getTCPPkt(ftcp.getFirstPayloadPacketNum()).getTCPData());
	        		   str = str.substring(0,str.indexOf("\r\n"));
	        		   str = str.replace(" ", "_");
	        		   str = str.replace("/", "_");
	        		   System.out.println(str);
	        		   TCPPacket pkts[] = ftcp.getReasmble(PacketSide.SERVER_TO_CLIENT);
	        		   FileOutputStream wr = new FileOutputStream(new File("/home/rbaryanai/strip/"+str));
	        		   String data = new String(pkts[0].getTCPData());
	        		   try{
	        		   data = data.substring(data.indexOf("\r\n\r\n")+"\r\n\r\n".length());
	        		   }
	        		   catch (Exception e) {
						
						break;
					}
	        		   wr.write(data.getBytes());
	        		   for(int i = 1 ; i<pkts.length ; i++){
	        			   wr.write(pkts[i].getTCPData());
	        		   }
	        		   wr.close();
	        	   }
	     
	           }
	
	     }
		 
	}
	
	
}
   


