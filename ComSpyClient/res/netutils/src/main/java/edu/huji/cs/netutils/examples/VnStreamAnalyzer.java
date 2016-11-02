package edu.huji.cs.netutils.examples;

public class VnStreamAnalyzer{
	/**
	 * This is the timestamp of the first packet that was used for the calculation.
	 * 
	 */
	public static final long UNINITIALIZED = -1;
	public static final long MAX_TCP_SEQ_VAL = (long) Math.pow(2, 32);
	private long timeStamp = 0;
	private long maxSeqNum = UNINITIALIZED;
	private long minSeqNum = UNINITIALIZED;
	private String srcIp;
	private String dstIp;
	private long wraparoundCount = 0;
	private long totalBytes = 0;
	
	
	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}


	/**
	 * This is a constructor for the stream analyzer.
	 * You must supply it with a timestamp of the first packet that is used for the calculation. 
	 * The timestamp is used by us for data analysis.
	 * @param timeStamp - in millis. 
	 * @param string2 
	 * @param string 
	 */
	public VnStreamAnalyzer(long timeStamp, String srcIp, String dstIp, long seqNum){
		this.timeStamp = timeStamp;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.minSeqNum = seqNum;
		this.maxSeqNum = seqNum;
		
	}
	
	public long getMinSeqNum() {
		return minSeqNum;
	}
	
	public void setMinSeqNum(long minSeqNum) {
		this.minSeqNum = minSeqNum;
	}
	
	public long getWraparoundCount() {
		return wraparoundCount;
	}
	
	public void setWraparoundCount(long wraparoundCount) {
		this.wraparoundCount = wraparoundCount;
	}
	
	public long getTotalBytes() {
		return totalBytes;
	}
	
	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}
	public long getMaxSeqNum() {
		return maxSeqNum;
	}

	
	public double getStreamEfficiency(){
		maxSeqNum += MAX_TCP_SEQ_VAL*wraparoundCount;
		return (double)(maxSeqNum - minSeqNum) / totalBytes;
	}
	
	/**
	 * This method sets the max seq num.
	 * @param newSeq
	 */
	public void setMaxSeqNum(long newSeq){
		if(minSeqNum>newSeq){
			wraparoundCount++;
		}
		maxSeqNum = newSeq + MAX_TCP_SEQ_VAL*wraparoundCount;
	}
	
	/**
	 * This method sets the max seq num considering the current packet length.
	 * In other words: It accepts bot the packet seq number and the packet total length and sums it.
	 * It makes calls more readable.
	 * @param newSeq
	 * @param packetLen
	 */
	public void setMaxSeqNum(long newSeq, long packetLen){
		newSeq = (newSeq + MAX_TCP_SEQ_VAL*wraparoundCount + packetLen);
		if(minSeqNum >newSeq){
			wraparoundCount++;
			maxSeqNum = newSeq;
		}else{
			if(maxSeqNum < newSeq){
				maxSeqNum = newSeq;
			}
		}
		
	}

	public void addTotalBytes(int payloadDataLength) {
		totalBytes+=payloadDataLength;
	}
	
	
}