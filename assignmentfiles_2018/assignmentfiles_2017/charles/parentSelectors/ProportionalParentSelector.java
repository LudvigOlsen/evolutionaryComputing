package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

import java.util.Random;

public class ProportionalParentSelector implements ParentSelector {

    private Random rand;
    private WeightedSampler sampler;
    private Population parents;

    public ProportionalParentSelector(Random rand) {
        this.rand = rand;
        sampler = new WeightedSampler(this.rand);
        parents = new Population();
    }

    @Override
    public Population selectParents(Population population, int numParents) {

        sampler.clearSampler(); // Remove previous mappings
        parents.clear();

        Double totalFitnessScore = (Double) population.getTotalFitnessScore();

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
//            System.out.print(individual.getFitnessScore());
//            System.out.print(" - ");
//            System.out.print(totalFitnessScore);
//            System.out.print(" - ");
//            System.out.println(individual.getFitnessScore() / totalFitnessScore);

            sampler.add(individual, (Double) individual.getFitnessScore() / totalFitnessScore);

        }

        for (int p = 0; p < numParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }
}
