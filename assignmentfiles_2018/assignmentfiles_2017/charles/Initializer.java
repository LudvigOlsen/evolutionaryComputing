package charles;

import charles.utils.ScaleToRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Initializer {

    private Random rand;

    public Population initialize(int numIndividuals,
                                 List<Integer> genomeSizes,
                                 List<Double> minLimits,
                                 List<Double> maxLimits,
                                 Random rand) {

        Population population = new Population();
        this.rand = rand;


        for (int i = 0; i < numIndividuals; i++) {
            population.addIndividual(createRandomIndividual(genomeSizes, minLimits, maxLimits));
        }

        return population;

    }

    private Individual createRandomIndividual(List<Integer> genomeSizes,
                                              List<Double> minLimits,
                                              List<Double> maxLimits) {

        final ArrayList<double[]> newGenome = new ArrayList<>();

        for (int i = 0; i < genomeSizes.size(); i++) {
            newGenome.add(createSingleGenomeArray(genomeSizes.get(i), minLimits.get(i), maxLimits.get(i)));
        }

        return new Individual(newGenome, minLimits, maxLimits);
    }

    private double[] createSingleGenomeArray(int genomeSize, double minLimit, double maxLimit) {
        double[] newGenomeArray = new double[genomeSize];
        for (int i = 0; i < genomeSize; i++) {
            // Generate genotype
            newGenomeArray[i] = ScaleToRange.scaleToRange(
                    rand.nextDouble(), 0.0,
                    1.0, minLimit, maxLimit);
        }

        return newGenomeArray;
    }
}
