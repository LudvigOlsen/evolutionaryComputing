package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StochasticIntermediateCrossoverRecombinator implements Recombinator {

    private Random rand;

    public StochasticIntermediateCrossoverRecombinator(Random rand) {
        this.rand = rand;
    }

    @Override
    public Individual combine(Population parents, int numCrossovers, List<Double> minLimits, List<Double> maxLimits) {
        int genomeSize = parents.getIndividual(0).getRepresentationSize();
        int numParents = parents.getPopulationSize();

        if (numParents > 2)
            throw new IllegalArgumentException("Exceeded max. number of parents. Max. is 2.");

        assert genomeSize >= numParents;

        ArrayList<double[]> childGenome = new ArrayList<>();

        for (int i = 0; i < minLimits.size(); i++) {
            childGenome.add(combineSingleGenomeArray(parents, i,
                    parents.getIndividual(0).getGenomeArraySize(i)));
        }

        return new Individual(childGenome, minLimits, maxLimits);
    }

    private double[] combineSingleGenomeArray(Population parents, int genomeIndex, int genomeSize) {

        double[] childGenomeArray = new double[genomeSize];

        for (int gt = 0; gt < genomeSize; gt++) {

            // Draw alpha with mean 0.5 and std.dev. 0.1
            // Limit to [0.0,1.0]
            double alpha = rand.nextGaussian() / 10 + 0.5;
            alpha = alpha > 1.0 ? 1.0 : alpha;
            alpha = alpha < 0.0 ? 0.0 : alpha;

            childGenomeArray[gt] = combineSingleGenotype(parents, gt, genomeIndex, alpha);
        }

        return childGenomeArray;

    }

    private double combineSingleGenotype(Population parents, int gtIndex, int genomeIndex, double alpha) {
        if (parents.getPopulationSize() > 2)
            throw new IllegalArgumentException("Exceeded max. number of parents. Max. is 2.");

        // Get parent alleles
        double p1 = parents.getIndividual(0).getGenome().get(genomeIndex)[gtIndex];
        double p2 = parents.getIndividual(1).getGenome().get(genomeIndex)[gtIndex];

        return alpha * p1 + (1 - alpha) * p2;

    }

}
