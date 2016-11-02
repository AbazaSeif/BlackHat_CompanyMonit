import java.util.Collections;
import java.util.Vector;

/*
 * Created on Jul 9, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author Fabian Jones AD Summer Intern 2003
 */
public class Simulation {
	private static int NUM_CHROMOSOMES = 10;
	private static int MAX_POWER = 10;
	private static int MAX_NUMBER = (int) Math.pow(2, MAX_POWER) - 1;
	private static int FITNESS_THRESHOLD = 5;
	private static float MUTATE = (float) .05;
	
	private Vector population;
	private boolean done = true;
	int numRuns = 0;
	
	
	public Simulation(){
		generateRandomPopulation();
	}
	
	private void generateRandomPopulation(){
		System.out.println("***Randomly Generating population with: " + NUM_CHROMOSOMES + " Chromosome(s)***");
		
		population = new Vector();
		
		for(int i = 0; i < NUM_CHROMOSOMES; ++i){
			int randomValue = (int) (Math.random()*MAX_NUMBER);
			population.add(new Chromosome(randomValue, MAX_POWER));
		}
		System.out.println("First Population: " + population +"\n");
	}
	
	public void start(){
		Collections.sort(population);
		Chromosome fitess = (Chromosome) population.lastElement();
		
		done = fitess.fitness() >= MAX_POWER? true:false;
		
		if(done){
			System.out.println("DONE, solution found: " + fitess);	
		}
		else{
			numRuns++;
			System.out.println("FITESS: " + fitess + " fitness: " + fitess.fitness());
			generateNewPopulation();
			start();
		}
	}
	
	private void generateNewPopulation(){
		
		System.out.println("***Generating New Population");
		Vector temp = new Vector();
		
		for(int i = 0; i < population.size()/2; ++i){
			Chromosome p1 = selectParent();
			Chromosome p2 = selectParent();
			temp.add(cross1(p1, p2));
			temp.add(cross2(p1, p2));
		}
		
		population.clear();
		population.addAll(temp);
		System.out.println("New Population: " + population + "\n");
	}
	
	private Chromosome selectParent(){
		int delta = population.size();
		delta = NUM_CHROMOSOMES - NUM_CHROMOSOMES/2;
		
		int num = (int) (Math.random()*10 + 1);
		int index;
		
		if(num >= 4){
			index = (int) (Math.random()*delta + NUM_CHROMOSOMES/2);
		}
		else{
			index = (int) (Math.random()*delta);	
		}
		
		return (Chromosome) population.get(index);
	}
	
	private Chromosome cross1(Chromosome parent1, Chromosome parent2){
		String bitS1 = parent1.getBitString();
		String bitS2 = parent2.getBitString();
		int length = bitS1.length();
		
		String newBitString = bitS1.substring(0, length/2) + bitS2.substring(length/2, length);
		Chromosome offspring = new Chromosome();
		offspring.setBitString(newBitString);
		
		if(shouldMutate()){
			mutate(offspring);
		}
		
		return offspring;
	}
	
	private Chromosome cross2(Chromosome parent1, Chromosome parent2){
		String bitS1 = parent1.getBitString();
		String bitS2 = parent2.getBitString();
		int length = bitS1.length();
		
		String newBitString = bitS2.substring(0, length/2) + bitS1.substring(length/2, length);
		Chromosome offspring = new Chromosome();
		offspring.setBitString(newBitString);
		
		if(shouldMutate()){
			mutate(offspring);
		}
		
		return offspring;
	}
	
	private boolean shouldMutate(){
		double num = Math.random();
		int number = (int) (num*100);
		num = (double) number/100;
		return (num <= MUTATE);
	}
	
	private void mutate(Chromosome offspring){
		String s = offspring.getBitString();
		int num = s.length();
		int index = (int) (Math.random()*num);
		String newBit = flip(s.substring(index, index+1));
		String newBitString = s.substring(0, index) + newBit + s.substring(index+1, s.length());
		offspring.setBitString(newBitString);
	}
	
	private String flip(String s){
		return s.equals("0")? "1":"0";
	}

	public static void main(String[] args) {
		double average = 0;
		int sum = 0;
		for(int i = 0; i < 10; ++i){
			Simulation s = new Simulation();
			s.start();
			sum = sum + s.numRuns;
			average = (double) sum / (double)(i+1); 
			System.out.println("Number of runs: " + s.numRuns);
		}
		System.out.println("average runs: " + average);
	}
}