package charles.survivalSelectors;

import charles.Population;

public class BestKYoungSurvivalSelector implements SurvivalSelector {

    @Override
    public Population selectSurvivors(Population population, int numSurvivors, int maxAge) {

        BestKSurvivalSelector bestKSelector = new BestKSurvivalSelector();

        // First remove individuals that are too old
        for (int i = 0; i < population.getPopulationSize(); i++) {
            if (population.getIndividual(i).getAge() > maxAge) {
                population.deleteIndividual(i);
            }
        }

        return bestKSelector.selectSurvivors(population, numSurvivors, maxAge);


    }
}
