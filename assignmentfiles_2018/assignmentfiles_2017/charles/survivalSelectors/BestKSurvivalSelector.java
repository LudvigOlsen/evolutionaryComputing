package charles.survivalSelectors;

import charles.Population;

import java.util.Collections;

public class BestKSurvivalSelector implements SurvivalSelector {

    @Override
    public Population selectSurvivors(Population population, int noSurvivors, int maxAge) {

        Population survivors = new Population();

        // Sort population by highest to lowest fitness
        population.getPopulation().sort(Collections.reverseOrder());

        // Copy survivors to new population
        for (int s = 0; s < noSurvivors; s++) {
            survivors.addIndividual(population.getIndividual(s));
        }

        return survivors;

    }
}
