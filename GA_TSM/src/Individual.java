



import java.util.Arrays;
import java.util.Random;
//Individual class
public class Individual implements Cloneable{
	public static final String BLACK = "\033[0;30m";   // BLACK
	public static final String RED = "\033[0;31m";     // RED
	public static final String GREEN = "\033[0;32m";   // GREEN
	public static final String TEXT_RESET = "\u001B[0m";
	private int geneLength;
	private int fitness = 0;
	private int[] genes;

	public Individual(int geneLength, int ind) {
		
		//Initialization
		this.geneLength = geneLength;
		this.genes = new int[geneLength];
		
		Random rn = new Random();
	
		//Set genes randomly for each individual
		for (int i = 0; i < genes.length; i++) {
			genes[i] = Problem.constraints[ind][i];
		}
	
		fitness = 0;
	}

	//Calculate fitness
	public void calcFitness() {
		fitness = 0;
		for (int i = 0; i < genes.length; i++) {
			if (genes[i] == 1) {
				++fitness;
			}
		}
	}
  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Individual individual = (Individual)super.clone();
		individual.genes = new int[geneLength];
		for(int i = 0; i < individual.genes.length; i++){
			individual.genes[i] = this.genes[i];
		}
		return individual;
	}

	@Override
	public String toString() {
		//without colors
		return "[genes=" + Arrays.toString(genes) + "]";
	}
  
	public String toStringColor() {
		//with colors
		String genesString = "[genes=[";
		int increment=0;
		for(int gene:genes) {
			//print gene
			if(gene == 0) genesString += BLACK + RED + gene + TEXT_RESET;
			if(gene == 1) genesString += BLACK + GREEN + gene + TEXT_RESET;
			//print comma
			if(increment<genes.length-1) genesString += ", ";
			
			increment++;
		}
		genesString += "]]";
		return genesString;
	}
  
	//Getters and Setters
  
	public int getGeneLength() {
		return geneLength;
	}

	public void setGeneLength(int geneLength) {
		this.geneLength = geneLength;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int[] getGenes() {
		return genes;
	}

	public void setGenes(int[] genes) {
		this.genes = genes;
	}

}