import java.lang.Comparable;

/*
 * Created on Jul 9, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author Fabian Jones AD Summer Intern 2003
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Chromosome implements Comparable{
	protected String bitString;
	
	public Chromosome(){
	}
	
	public Chromosome(int value, int length){
		bitString = convertIntToBitString(value, length);
	}
	
	public void setBitString(String s){
		bitString = s;
	}
	
	public String getBitString(){
		return bitString;
	}
	
	public int compareTo(Object o) {
		Chromosome c = (Chromosome) o;
		int num = countOnes(this.bitString) - countOnes(c.getBitString()); 
		return num;
	}
	
	public int fitness(){
		return countOnes(bitString);
	}
	
	public boolean equals(Object o){
		if(o instanceof Chromosome){
			Chromosome c = (Chromosome) o;
			return c.getBitString().equals(bitString);
		}
		return false;
	}
	
	public int hashCode(){
		return bitString.hashCode();
	}
	
	public String toString(){
		return "Chromosome: " + bitString;
	}
	
	public static int countOnes(String bits){
		int sum = 0;
		for(int i = 0; i < bits.length(); ++ i){
			String test = bits.substring(i, i+1);
			if(test.equals("1")){
				sum = sum + 1;
			}
		}
		return sum;
	}
	
	public static String convertIntToBitString(int val, int length){
		int reval = val;
		
		StringBuffer bitString = new StringBuffer(length);
		for(int i = length-1; i >=0; --i ){
			if( reval - (Math.pow(2, i)) >= 0 ){
				bitString.append("1");
				reval = (int) (reval - Math.pow(2, i));
			}
			else{
				bitString.append("0");
			}
		}
		return bitString.toString();
	}
	
	public static void main(String[] args){
		//System.out.println(convertIntToBitString(2046, 10));
		Chromosome c = new Chromosome(1234, 10);
		System.out.println(c.fitness());
	}
}