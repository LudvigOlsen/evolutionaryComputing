package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;
import java.util.List;
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
    public Individual combine(Population parents, int numCrossovers, List<Double> minLimits, List<Double> maxLimits) {
        int genomeSize = parents.getIndividual(0).getRepresentationSize();
        int numParents = parents.getPopulationSize();

        assert genomeSize >= numParents;

        ArrayList<double[]> childGenome = new ArrayList<>();

        for (int i = 0; i < minLimits.size(); i++) {
            childGenome.add(combineSingleGenomeArray(parents, i,
                    parents.getIndividual(0).getGenomeArraySize(i),
                    numParents));
        }


        return new Individual(childGenome, minLimits, maxLimits);
    }

    private double[] combineSingleGenomeArray(Population parents, int genomeIndex, int genomeSize, int numParents) {

        double[] childGenomeArray = new double[genomeSize];
        for (int gt = 0; gt < genomeSize; gt++) {
            int fromParent = (int) Math.round(rand.nextDouble() * (numParents - 1));
            childGenomeArray[gt] = parents.getIndividual(fromParent).getGenome().get(genomeIndex)[gt];
        }

        return childGenomeArray;

    }

}
