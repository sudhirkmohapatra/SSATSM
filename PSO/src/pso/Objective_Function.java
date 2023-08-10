package pso;

import java.util.Random;

public class Objective_Function {
    public static double[] iPOP_Fit(int item[]) {
        double check = 0, no_requirment_addrressed, no_total_requirment;
        double requirment_score = 0.0D, m_score_compliment = 0.0D;
        int i, Pyes = 0, PTmutant = 0, PNoope = 0;
        int[] TempItem = new int[item.length];
        for (i=0;i < item.length;i++) {
            TempItem[i] = item[i];
            if (TempItem[i] == 1) {
                Pyes++;
            }
            if (TempItem[i] == 0) {
                PTmutant++;
            }
        }
        Random r = new Random();
        double randomValue = 0 + (1 - 0.0D) * r.nextDouble();
        no_total_requirment = item.length;
        no_requirment_addrressed = Pyes;
        PNoope = (PTmutant - Pyes);
        if (no_total_requirment != check) {
            requirment_score = ((((no_requirment_addrressed) / (no_total_requirment)) * 100) + randomValue);
            m_score_compliment = 100 - requirment_score;

        }
        return (new double[]{requirment_score, m_score_compliment, Pyes, PTmutant,item.length});
    }
}
