/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso;

import java.util.Arrays;

/**
 *
 * @author Aliazar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
  
	public static void main(String[] args) throws Exception {
		Problem.main123();
		try {
			StdOut.println("*******************************************");
			StdOut.printf("Solving %s\n", Problem.name);
			StdOut.printf("Total number of Test requirements :%d\n", Problem.constraints.length);
			for (int k = 0; k < 1; k++) {
				StdRandom.newSeed();
				(new Swarm()).execute();
			}
			StdOut.printf("Ending %s\n", Problem.name);
			StdOut.println("*******************************************");

			double []arr = Objective_Function.iPOP_Fit(Particle.pBest);
			System.out.println("Total number of Test Cases :"+Particle.pBest.length);
			System.out.println("No of Test cases in reduced set :"+arr[2]);

			System.out.println("Cost :"+Arrays.toString(Problem.costs));

		} catch (Exception e) {
			StdOut.printf("%s\n%s\n", e.getMessage(), e.getCause());
		}
	}
}
    

