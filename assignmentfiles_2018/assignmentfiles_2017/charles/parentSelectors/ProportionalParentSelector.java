package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

import java.util.Random;

public class ProportionalParentSelector implements ParentSelector {

    private Random rand;
    private WeightedSampler sampler;
    private Population parents;
    private double fitnessBias; // TODO Adds bias to make sure we don't get negative probability - e.g. 5000 
                                // (This decreases the proportional differences though. See book)

    public ProportionalParentSelector(Random rand, double fitnessBias) {
        this.rand = rand;
        sampler = new WeightedSampler(this.rand);
        parents = new Population();
        this.fitnessBias = fitnessBias;
    }

    @Override
    public Population selectParents(Population population, int numParents) {

        sampler.clearSampler(); // Remove previous mappings
        parents.clear();

        double totalFitnessScore = population.getTotalFitnessScore() + fitnessBias * population.getPopulationSize();

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);

            sampler.add(individual, (individual.getFitnessScore() + 1E-5 + fitnessBias) / (totalFitnessScore + 1E-5));

        }

        for (int p = 0; p < numParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }
}
