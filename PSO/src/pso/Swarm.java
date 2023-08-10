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
import java.util.ArrayList;

public class Swarm {

    private final int T = 50; //Termination condition or number of total iteration
	private final int nParticles = Problem.constraints.length; // Total number of population
    private final float w = 0.5f, c1 = 0.6f, c2 = 0.6f; // w -inertia of the particle, c1, c2 - acceleration coefficients



    private ArrayList<Particle> swarm = null;
    private Particle g = null;

	private long sTime, eTime;

    public void execute() {
		startTime();
        init();
        run();
		endTime();
		log();
    }

	private void startTime() {
		sTime = System.currentTimeMillis();
	}

    private void init() {
		swarm = new ArrayList<>();
		g = new Particle(); // g Global best
        Particle p = null;
        for (int i = 1; i <= nParticles; i++) {
            p = new Particle();
            if (!p.isFeasible()) {
				p.repare();
			}
            p.updatePBest();
            swarm.add(p);
        }
        g.copy(swarm.get(0));
        for (int i = 1; i < nParticles; i++) {
            if (swarm.get(i).isBetterThan(g)) {
                g.copy(swarm.get(i));
            }
        }
    }

    private void run() {
        int t = 1;

        while (t <= T) {

            for (int i = 0; i < nParticles; i++) {
                if (swarm.get(i).isBetterThanPBest()) {
                    swarm.get(i).updatePBest();
                }
                if (swarm.get(i).isBetterThan(g)) {
                    g.copy(swarm.get(i));
                }
            }
            for (int i = 0; i < nParticles; i++) {
                swarm.get(i).move(g, w, c1, c2);
                if (!swarm.get(i).isFeasible()) {
					swarm.get(i).repare();
				}
            }

            t++;
        }
    }

	private void endTime() {
		eTime = System.currentTimeMillis();
	}

    private void log() { StdOut.printf("%s, s: %s, t: %s\n", g, StdRandom.getSeed(), (eTime - sTime)/100); } // s - seed t-time
}