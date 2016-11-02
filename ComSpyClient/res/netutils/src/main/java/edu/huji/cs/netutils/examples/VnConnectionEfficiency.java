package edu.huji.cs.netutils.examples;

import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.huji.cs.netutils.NetUtilsException;
import edu.huji.cs.netutils.capture.analyze.CaptureFileFlowAnalyzer;
import edu.huji.cs.netutils.capture.analyze.Flow;
import edu.huji.cs.netutils.capture.analyze.PacketType;
import edu.huji.cs.netutils.files.pcap.PCapFileReader;
import edu.huji.cs.netutils.files.pcap.PCapFileWriter;
import edu.huji.cs.netutils.parse.FiveTuple;
import edu.huji.cs.netutils.parse.IPPacket;
import edu.huji.cs.netutils.parse.IPPacketBase;
import edu.huji.cs.netutils.parse.IPv4Packet;
import edu.huji.cs.netutils.parse.TCPPacket;
import edu.huji.cs.netutils.parse.UDPPacket;

/**
 * This class analyzes a capture file to determine the connection efficiency
 * factor.
 * 
 * The factor is calculated thus: a TCP connection is analyzed for the initial
 * seq number, highest seq number reached, and total of payload bytes
 * transported. Then: (maxSeq - minSeq) / total transported bytes
 * 
 * @author jbord
 * 
 */
public class VnConnectionEfficiency {

	/**
	 * A map the maps time windows to the flows that exist at that window
	 * The map of flows maps the FiveTuple of each flow to a list.
	 * The list contains the flows that existed in that windows by that map. 
	 * This is done in case the same window has several flows from the same FiveTuple.
	 */
	private Map<Long, Map<FiveTuple, List<VnStreamAnalyzer>>> m = 
			new HashMap<Long, Map<FiveTuple, List<VnStreamAnalyzer>>>();
	private Set<FiveTuple> tuples = new HashSet<FiveTuple>();
	private Set<String> servers = new HashSet<String>(); //this is a list of content servers IP addresses, that the UEs are downloading from in this cap.
	private long windowSize = 5; //in seconds.
	public static void main(String[] args) throws IOException,
			NetUtilsException, InterruptedException {
		VnConnectionEfficiency ce = new VnConnectionEfficiency();
		if(args.length < 1){
			System.out.print("Need to specify the location of the PCAP file.");
		}
		ce.analyzePcap(
		 //"/home/jbord/Downloads/120516_inline_6dl_3abr_nov6.cap");
		//		"/home/jbord/Downloads/sample.pcap");
		args[0]);
		
		
	}
	
	byte[] ethHeader = {0, (byte) 0xd0, (byte) 0xc9, (byte) 0xbf, (byte) 0x08,(byte) 0x4e,(byte) 0xc4,(byte) 0x71,
			(byte) 0xfe, (byte) 0x03, (byte) 0x70, (byte) 0x0, (byte) 0x81, (byte) 0x0, (byte) 0x01, (byte) 0x3b,
			(byte) 0x08 ,(byte) 0x00};
	
	public void analyzePcap(String pcap) throws NetUtilsException, IOException, InterruptedException{
		PCapFileReader fr = new PCapFileReader(pcap);
		PCapFileWriter pc = new PCapFileWriter("/tmp/pcap");
		byte[] pkt = fr.ReadNextPacket();
		while(pkt!=null){
			IPv4Packet ippkt = new IPv4Packet(pkt);
			if(ippkt.getIPProtocol() == 0x11){ //is this udp?
				UDPPacket udppkt = new UDPPacket(pkt);
				if(udppkt.getDestinationPort() == 2152){ //is this GTP?
					if(udppkt.getUDPData()[1]== (byte) 0xff){ //is this a GTP payload packet?
						ByteBuffer bf =  ByteBuffer.allocate(ethHeader.length + udppkt.getUDPDataLength() - 8);
						bf.put(ethHeader);
						bf.put(udppkt.getUDPData(),8, udppkt.getUDPDataLength() - 8  );
						pc.addPacket(bf.array(),fr.getTimeStamp());
					}
				}
			}
			pkt = fr.ReadNextPacket();
		}
		pc.close();
		CaptureFileFlowAnalyzer an = new CaptureFileFlowAnalyzer("/tmp/pcap");
		long initialTs = an.getPktTimeStamp(1);
		long maxIdx = 0;
		for (Flow f : an) {
			if (f.getFlowType() == PacketType.TCP) {
				//we only care about TCP packets.
				for (int i = 1; i <= f.getNumOfPkts(); i++) {
					byte[] p = f.getPkt(i);
					long timestamp = f.getPktTimeStamp(i);
					TCPPacket tcppkt = new TCPPacket(p);
					FiveTuple tuple = new FiveTuple(p);
					int add = 0;
					if(tcppkt.isFin() || tcppkt.isSyn()){
						add = 1;
					}
					
					VnStreamAnalyzer analyzer;
					long idx = (timestamp- initialTs)/(windowSize*1000000); //windowsize is given in seconds, timestamp in milis
					if(!m.containsKey(idx)){
						m.put((long)idx, new HashMap<FiveTuple,List<VnStreamAnalyzer>>());
					}
					if(idx > maxIdx){
						maxIdx = idx;
					}
					if(tcppkt.isSyn() && tcppkt.isAck()){
						//if this packet is a syn-ack, that means the src IP is "up" and the dst ip is "down". 
						//we assume that practically all connections are initiated by the UE. 
						tuples.add(tuple);
						servers.add(tcppkt.getUnderlyingIPPacketBase().getSourceIPAsString());
						analyzer = new VnStreamAnalyzer(timestamp, 
								tcppkt.getUnderlyingIPPacketBase().getSourceIPAsString(),
								tcppkt.getUnderlyingIPPacketBase().getDestinationIPAsString(),
								tcppkt.getSequenceNumber());
						analyzer.setMaxSeqNum(tcppkt.getSequenceNumber(), tcppkt.getPayloadDataLength()+add);
						analyzer.addTotalBytes(tcppkt.getPayloadDataLength()+add);
						if(m.get(idx).containsKey(tuple)){
							//if this window already had flows from this tuple.
							m.get(idx).get(tuple).add(analyzer);
						}else{
							//otherwise, we create a new list.
							m.get(idx).put(tuple, new LinkedList<VnStreamAnalyzer>());
							m.get(idx).get(tuple).add(analyzer);
						}
					}else if(tcppkt.isRst()){
							continue;
					}else{
						//the packet is not a syn-ack, we might need to use an existing analyzer.
						if(m.get(idx).containsKey(tuple)){
							//if we already seen packets from this flow in this time-window.
							List<VnStreamAnalyzer> l = m.get(idx).get(tuple);
							analyzer = l.get(l.size()-1);
							if(analyzer.getSrcIp().equals(tcppkt.getUnderlyingIPPacketBase().getSourceIPAsString())){
								//if this packet is headed towards the UE.
								analyzer.setMaxSeqNum(tcppkt.getSequenceNumber(), tcppkt.getPayloadDataLength()+add);
								analyzer.addTotalBytes(tcppkt.getPayloadDataLength()+add);
							}
							
						}else{
							//if this is the first time we see this tuple in this time-window
							if(servers.contains(tcppkt.getUnderlyingIPPacketBase().getSourceIPAsString())){
								tuples.add(tuple);
								analyzer = new VnStreamAnalyzer(timestamp, 
										tcppkt.getUnderlyingIPPacketBase().getSourceIPAsString(),
										tcppkt.getUnderlyingIPPacketBase().getDestinationIPAsString(),
										tcppkt.getSequenceNumber());
								analyzer.setMaxSeqNum(tcppkt.getSequenceNumber(), tcppkt.getPayloadDataLength()+add);
								analyzer.addTotalBytes(tcppkt.getPayloadDataLength()+add);
								m.get(idx).put(tuple, new LinkedList<VnStreamAnalyzer>());
								m.get(idx).get(tuple).add(analyzer);
							}
						}
					}
				}
			}
		}
		FileWriter f= new FileWriter(new File("/tmp/output.csv"));
		//we print all the data
		for(FiveTuple ft : tuples){
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Src: %s:%d, Dst: %s:%d,", ft.getMySrcIpAsString(),ft.getMySrcPort(), ft.getMyDstIpAsString(), ft.getDstPort()));
			for(long i = 0; i <= maxIdx ; i++ ){
				if(m.containsKey(i)){
					//if we saw any packets from this timeslot
					if(m.get(i).containsKey(ft)){
						List<VnStreamAnalyzer> l = m.get(i).get(ft);
						//if we have more than one flow from the same tuple in this window, we average its efficiency.
						double avg = 0;
						for(VnStreamAnalyzer vsa : l){
							avg += vsa.getStreamEfficiency();
						}
						avg /= l.size();
						if(Double.isNaN(avg)||Double.isInfinite(avg) || avg > 1){
							avg = 1.0;
						}
						sb.append(avg);
						sb.append(",");
					}else{
						//if this tuple didn't appear on this timeslot
						sb.append("1,");
					}
				}else{
					//if no packets, than this flow didn't appear.
					sb.append("1,");
				}
			}
			f.write(sb.toString() + "\n");
			System.out.print(sb.toString() + "\n");
		}
		f.close();
	}

}
