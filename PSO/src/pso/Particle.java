/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso;

/**
 *
 * @author Aliazar
 */
import java.util.Arrays;

public class Particle {

    protected static int nVariables = Problem.dimension;

	private int[] x = new int[nVariables];         //x is position of the particle
    public static int[] pBest = new int[nVariables];   //personal best of the particle
    private double[] v = new double[nVariables];  //v is velocity of the particle

    public Particle() { //Generates random position within [0,1] and velocity within the domain 0<xi<=1
        for (int j = 0; j < nVariables; j++) {
            x[j] = StdRandom.uniform(2);
            v[j] = StdRandom.uniform();
        //    System.out.print(" X "+x[j]+" ");
         //   System.out.print(" V "+v[j]);

        }
    }

    protected boolean isBetterThanPBest() {
        return fitness() < fitnessPBest();
    } // fitnessPBest is better than fitness

    protected boolean isBetterThan(Particle g) {  return fitnessPBest() < g.fitnessPBest(); } // global best is better than personal best

    protected void updatePBest() { System.arraycopy(x, 0, pBest, 0, x.length); } // if the condition is satisfied then updatePBest

    private float fitness() { 
        float sum = 0; 
        for (int j = 0; j < nVariables; j++) {
        	sum += x[j] * Problem.costs[j];
        }
        return sum;
    }

    private float fitnessPBest() {
        float sum = 0;
        for (int j = 0; j < nVariables; j++) {
        	sum += pBest[j] * Problem.costs[j];

        }
        return sum;

    }

    protected boolean isFeasible() {
        int sum = 0;
        int i = 0;
        boolean feasible = true;
        while (i < Problem.constraints.length && feasible) {
        	for (int j = 0; j < nVariables; j++) {
        		sum += x[j] * Problem.constraints[i][j];
        	}
        	feasible = sum >= 1;
        	sum = 0; 
        	i++;
        }
        return feasible;
    }

	protected void repare() {
		int worstContributionIndex = 0, sum;
		double worstContribution = 0;
		double contributionLevel[] = new double [nVariables];

		for(int j = 0; j < contributionLevel.length; j++) {
			sum = 0;
			for(int i = 0; i < Problem.constraints.length; i++) {
				sum += Problem.constraints[i][j];
			}
			contributionLevel[j] = (contributionLevel.length / sum);
		}

		do {
			worstContribution = contributionLevel[worstContributionIndex];
			for(int j = 0; j < contributionLevel.length; j++) {
				if (contributionLevel[j] < worstContribution) {
					worstContribution = contributionLevel[j];
					worstContributionIndex = j;
				}
			}

			x[worstContributionIndex] = 1;
			contributionLevel[worstContributionIndex] = Double.MAX_VALUE;
		} while(!isFeasible());
	}

    protected void move(Particle g, float w, float c1, float c2) {
        for (int j = 0; j < nVariables; j++) {
            /* update velocity */
            v[j] = v[j] * w
                    + c1 * StdRandom.uniform() * (pBest[j] - x[j])
                    + c2 * StdRandom.uniform() * (g.pBest[j] - x[j]);
            /* update position */
            x[j] = BinarizationStrategy.toBinary(x[j] + v[j]); // bounding [0,1]

        }
    }

	private float optimal() {
		return Problem.optimal;
	}

	private float diff() {
		return fitnessPBest() - optimal();
	} //diff == fitnessBest - 100(optimal)

	private float rpd() {
		return diff() / optimal() * 100f;
	} /// rpd == diff() / optimal() *100

    protected void copy(Object object)  {
        if (object instanceof Particle) {
            System.arraycopy(((Particle) object).x, 0, this.x, 0, nVariables);
            System.arraycopy(((Particle) object).pBest, 0, this.pBest, 0, nVariables);
            System.arraycopy(((Particle) object).v, 0, this.v, 0, nVariables);
        }
    }

    @Override
    public String toString() {
		return String.format(
			"optimal value: %.1f, gBest-fitness: %.1f, diff: %s, rpd: %.2f%%, gBest: %s",
			optimal(), 
			fitnessPBest(), 
			diff(),
			rpd(),
			Arrays.toString(pBest)
		);
    }
}