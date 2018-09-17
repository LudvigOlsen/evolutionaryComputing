package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.Random;

public class UniformRecombinator implements Recombinator {

    private Random rand;

    public UniformRecombinator(Random rand) {
        this.rand = rand;
    }

    /*
    This recombiner merges n parents by randomly selecting genotypes
    from each of the n parents. (Uniform distribution)

    E.g. select from individuals | 0, 1, 3, 2, 3, 0 |
    
    Note: Does not use numCrossovers parameter as all params are randomly selected.

     */
    @Override
    public Individual combine(Population parents, int numCrossovers, double minLimit, double maxLimit) {
        int genomeSize = parents.getIndividual(0).getGenomeSize();
        int numParents = parents.getPopulationSize();

        assert genomeSize >= numParents;

        double[] childGenome = new double[genomeSize];

        for (int gt = 0; gt < genomeSize; gt++) {
            int fromParent = (int) Math.round(rand.nextDouble() * (numParents - 1));
            childGenome[gt] = parents.getIndividual(fromParent).getGenome()[gt];
        }

        return new Individual(childGenome, minLimit, maxLimit);
    }

}
