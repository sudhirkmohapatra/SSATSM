


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SimpleDemoGA {

	private Population population;
	private Individual fittest;
	private Individual secondFittest;
	private int generationCount;
	private static int numberOfGenes;
    private static int numberOfIndividuals;
    private static int limit=50;
	private static boolean verbose;
	private static boolean coloredGenes;
	protected static ArrayList<Particle> Person = null;
	protected static Particle g = null;
	private static long sTime, eTime;
    public SimpleDemoGA() {
		this.population = new Population(numberOfIndividuals, numberOfGenes);
		this.generationCount = 0;
	}
    public static void main(String[] args) throws Exception {SimpleDemoGA.iGABootStarter(); }
    private static void iGABootStarter() throws Exception {
		Problem.main123();
        try {
			StdOut.println("*******************************************");
			StdOut.printf("Solving %s\n", Problem.name);
			StdOut.printf("Total number of Test requirements :%d\n", Problem.constraints.length);
            for (int k = 0; k < 1; k++) {
                startTime();
                execute();
                endTime();
                log();
            }

            StdOut.println("*******************************************");
			double []arr = Objective_Function.iPOP_Fit(Particle.pBest);
			System.out.println("Total number of Test Cases :"+Particle.pBest.length);
			System.out.println("No of Test cases in reduced set :"+arr[2]);

			System.out.println("Cost :"+Arrays.toString(Problem.costs));
        } catch (Exception e) {
            StdOut.printf("%s\n%s\n", e.getMessage(), e.getCause());
        }

    }

    public static void execute() {

        Random rn = new Random();

		//Set parameters here

		//Number of genes each individual has
		numberOfGenes = Problem.constraints[0].length;
		//Number of individuals
		numberOfIndividuals = Problem.constraints.length;
		//Verbosity (e.g. Should we print genetic pool in the console?)
		verbose = true;
		//Apply color to genes (if verbose = true) Note: this will slow down the process
		coloredGenes = true;
        
		//===================
        
		//Initialize population
		SimpleDemoGA demo = new SimpleDemoGA();
        
		demo.population = new Population(numberOfIndividuals, numberOfGenes);
        
		//System.out.println("Population of "+demo.population.getPopSize()+" individual(s).");

		//Calculate fitness of each individual
		demo.population.calculateFitness();

		//System.out.println("\nGeneration: " + demo.generationCount + " Fittest: " + demo.population.getFittestScore());
		//show genetic pool
		showGeneticPool(demo.population.getIndividuals());

		//While population gets an individual with maximum fitness
		while (demo.generationCount < limit) {
			++demo.generationCount;

			for(int i=0;i<numberOfIndividuals;i++)
			{
				if (Person.get(i).isBetterThanPBest()) {
					Person.get(i).updatePBest();
				}
				if (Person.get(i).isBetterThan(g)) {
					g.copy(Person.get(i));
				}
			}
			//Do selection
			demo.selection();

			//Do crossover
			demo.crossover();
            for(int i=0;i<numberOfIndividuals;i++)
            {if (!Person.get(i).isFeasible()) {Person.get(i).repare();}}

			//Do mutation under a random probability
			if (rn.nextInt()%7 < 5) {
				demo.mutation();
			}

			//Add fittest offspring to population
			demo.addFittestOffspring();

			//Calculate new fitness value
			demo.population.calculateFitness();

//			System.out.println("\nGeneration: " + demo.generationCount + " Fittest score: " + demo.population.getFittestScore());
            
			//show genetic pool
			showGeneticPool(demo.population.getIndividuals());
//            System.out.println(""+ Arrays.toString(Particle.pBest)+"     "+ Arrays.toString(Particle.pBest));
        }


       // System.out.println(" "+new Particle().toString());

	}

	//Selection
	void selection() {

		//Select the most fittest individual
		fittest = population.selectFittest();

		//Select the second most fittest individual
		secondFittest = population.selectSecondFittest();
	}

	//Crossover
	void crossover() {
		Random rn = new Random();

		//Select a random crossover point
		int crossOverPoint = rn.nextInt(this.numberOfGenes);

		//Swap values among parents
		for (int i = 0; i < crossOverPoint; i++) {
			int temp = fittest.getGenes()[i];
			fittest.getGenes()[i] = secondFittest.getGenes()[i];
			secondFittest.getGenes()[i] = temp;
            Particle.x[i] =(fittest.getGenes()[i]);
		}

	}

	//Mutation
	void mutation() {
		Random rn = new Random();

		//Select a random mutation point
		int mutationPoint = rn.nextInt(this.numberOfGenes);

		//Flip values at the mutation point
		if (fittest.getGenes()[mutationPoint] == 0) {
			fittest.getGenes()[mutationPoint] = 1;
		} else {
			fittest.getGenes()[mutationPoint] = 0;
		}

		mutationPoint = rn.nextInt(this.numberOfGenes);

		if (secondFittest.getGenes()[mutationPoint] == 0) {
			secondFittest.getGenes()[mutationPoint] = 1;
		} else {
			secondFittest.getGenes()[mutationPoint] = 0;
		}
	}

	//Get fittest offspring
	Individual getFittestOffspring() {
		if (fittest.getFitness() > secondFittest.getFitness()) {
			return fittest;
		}
		return secondFittest;
	}

	//Replace least fittest individual from most fittest offspring
	void addFittestOffspring() {

		//Update fitness values of offspring
		fittest.calcFitness();
		secondFittest.calcFitness();

		//Get index of least fit individual
		int leastFittestIndex = population.getLeastFittestIndex();

		//Replace least fittest individual from most fittest offspring
		population.getIndividuals()[leastFittestIndex] = getFittestOffspring();
	}
    
	//show genetic state of the population pool
	static void showGeneticPool(Individual[] individuals) {
		if(!verbose) return;

	}

    private static void startTime() {
        sTime = System.currentTimeMillis();
    }

    private static void endTime() {
        eTime = System.currentTimeMillis();
    }

    private static void log() { StdOut.printf("%s, s: %s, t: %s\n", g, StdRandom.getSeed(), (eTime - sTime)/100); } // s - seed t-time


}