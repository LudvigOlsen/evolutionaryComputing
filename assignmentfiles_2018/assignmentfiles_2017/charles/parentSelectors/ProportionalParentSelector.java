package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

public class ProportionalParentSelector implements ParentSelector {

    @Override
    public Population selectParents(Population population, int noParents) {

        WeightedSampler<Individual> sampler = new WeightedSampler<>();
        Population parents = new Population();
        double totalFitnessScore = population.getTotalFitnessScore();

        for (int i = 0; i < population.getPopulationSize(); i++) {
            Individual individual = population.getIndividual(i);
            sampler.add(individual.getFitnessScore() / totalFitnessScore, individual);
        }

        for (int p = 0; p < noParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }
}
