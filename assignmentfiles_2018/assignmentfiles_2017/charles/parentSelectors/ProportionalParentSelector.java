package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

import java.util.Random;

public class ProportionalParentSelector implements ParentSelector {

    private Random rand;

    public ProportionalParentSelector(Random rand) {
        this.rand = rand;
    }

    @Override
    public Population selectParents(Population population, int numParents) {

        WeightedSampler<Individual> sampler = new WeightedSampler<>(this.rand);
        Population parents = new Population();
        double totalFitnessScore = population.getTotalFitnessScore();

        for (int i = 0; i < population.getPopulationSize(); i++) {
            Individual individual = population.getIndividual(i);
            sampler.add(individual.getFitnessScore() / totalFitnessScore, individual);
        }

        for (int p = 0; p < numParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }
}
