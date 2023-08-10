package com.bat;

import java.util.Arrays;

public class Bat {



    protected static int nVariables = Problem.dimension;


    private double f;
      static double[] v = new double[nVariables];
    public static int[] xBest = new int[nVariables];
    private final int[] x = new int[nVariables];
    protected double A;
    private double r;

    public Bat() {
        for (int j = 0; j < nVariables; j++) {
            v[j] = StdRandom.uniform();
            x[j] = StdRandom.uniform(2);
            A = StdRandom.uniform();
            r = StdRandom.uniform();
        }
    }
    protected boolean isBetterThanXBest() {
        return fitness() < fitnessXBest();
    }
    protected boolean isBetterThan(final Bat g) {  return fitnessXBest() < g.fitnessXBest(); }
    protected void updateXBest() { System.arraycopy(x, 0, xBest, 0, x.length); }

    private float fitness() {
        float sum = 0;
        for (int j = 0; j < nVariables; j++) {
            sum+= x[j] * Problem.costs[j];
        }
        return sum;
    }
    private float fitnessXBest() {
        float sum = 0;
        for (int j = 0; j < nVariables; j++) {
            sum += xBest[j] * Problem.costs[j];
            if (sum==100){Swarm.noIterationCheck = false;}
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


    protected boolean hasExploration() {
        return r < StdRandom.uniform();
    }

    protected boolean hasExploitation() {
        return A > StdRandom.uniform();
    }

    protected void decreasesLoudness(final double alpha) {
        A = alpha * A;
    }

    protected void increasesPulseEmission(final double gamma, final  int t) {
        r = r * (1 - (Math.pow(Math.E, (-gamma * t))));
    }

    protected void randomWalk(final double epsilon, final double avgA) {
        for (int j = 0; j < nVariables; j++) {
            x[j] = BinarizationStrategy.toBinary(x[j] + epsilon * avgA);
        }
    }

    protected void move(final Bat g, final double fmin, final double fmax) {
        final double beta = StdRandom.uniform();
        f = fmin + (fmax - fmin) * beta;
        for (int j = 0; j < nVariables; j++) {
            v[j] = v[j] + (x[j] - g.x[j]) * f;
            x[j] = BinarizationStrategy.toBinary(x[j] + v[j]);

        }
    }

    private float optimal() {
        return Problem.optimal;
    }

    private float diff() {
        return fitnessXBest()- optimal();
    }

    private float rpd() {
        return diff() / optimal() * 100f;
    }

    protected void copy(final Object object) {
        if (object instanceof Bat) {
            System.arraycopy(((Bat) object).v, 0, v, 0, nVariables);
            System.arraycopy(((Bat) object).xBest, 0, this.xBest, 0, nVariables);
            System.arraycopy(((Bat) object).x, 0, x, 0, nVariables);
            A = ((Bat) object).A;
            r = ((Bat) object).r;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "optimal value: %5.1f, fitnessXBest: %5.1f, diff: %5s, rpd: %5.2f%%, xBest: %5s",
                optimal(),
                fitnessXBest(),
                diff(),
                rpd(),
                Arrays.toString(xBest)
        );
    }
}