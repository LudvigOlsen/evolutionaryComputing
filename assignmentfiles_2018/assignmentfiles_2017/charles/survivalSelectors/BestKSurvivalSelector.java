package charles.survivalSelectors;

import charles.Population;

import java.util.Collections;

public class BestKSurvivalSelector implements SurvivalSelector {

    @Override
    public Population selectSurvivors(Population population, int numSurvivors, int maxAge) {

        Population survivors = new Population();

        // Sort population by highest to lowest fitness
        population.getPopulation().sort(Collections.reverseOrder());

        // Copy survivors to new population
        for (int s = 0; s < numSurvivors; s++) {
            survivors.addIndividual(population.getIndividual(s));
        }

        // Check survivors' fitnessScore
//        for (int s = 0; s < numSurvivors; s++) {
//            System.out.println(survivors.getIndividual(s).getFitnessScore());
//        }

        return survivors;

    }
}
