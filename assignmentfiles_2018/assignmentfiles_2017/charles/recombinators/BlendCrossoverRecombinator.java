package charles.recombinators;

import charles.Individual;
import charles.Population;
import charles.utils.ScaleToRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// (BLX − α)
public class BlendCrossoverRecombinator implements Recombinator {

    private Random rand;
    private double alpha;

    public BlendCrossoverRecombinator(Random rand, double alpha) {
        this.rand = rand;
        this.alpha = alpha;
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
            childGenomeArray[gt] = combineSingleGenotype(parents, gt, genomeIndex, alpha);
        }

        return childGenomeArray;

    }

    private double combineSingleGenotype(Population parents, int gtIndex, int genomeIndex, double alpha) {
        if (parents.getPopulationSize() > 2)
            throw new IllegalArgumentException("Exceeded max. number of parents. Max. is 2.");

        // Init x and y
        double x;
        double y;

        // Get parent alleles
        double p1 = parents.getIndividual(0).getGenome().get(genomeIndex)[gtIndex];
        double p2 = parents.getIndividual(1).getGenome().get(genomeIndex)[gtIndex];

        // x is the smallest parent, y is the largest parent
        if (p1 < p2) {
            x = p1;
            y = p2;
        } else {
            x = p2;
            y = p1;
        }

        double d = y - x;
        double u = rand.nextDouble();

        // Sample uniformly from range [xi −α· di, xi + α · di]
        return ScaleToRange.scaleToRange(u, 0, 1, x - alpha * d, x + alpha * d);

        // [xi −α· di, xi + α · di]
        //double l = ((1 - (2 * alpha)) * u) - alpha; THIS WOULD NOT BE STOCHASTIC WHEN alpha=0.5!
        //return (1-l)*x+l*y; //(1 − γ)xi + γyi

    }

}
