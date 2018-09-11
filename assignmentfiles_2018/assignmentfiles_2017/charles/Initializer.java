package charles;

import charles.utils.ScaleToRange;

import java.util.Random;

public class Initializer {

    public Population initialize(int numIndividuals, int genomeSize, double minLimit, double maxLimit, Random rand) {

        Population population = new Population();

        for (int i = 0; i < numIndividuals; i++) {
            population.addIndividual(createRandomIndividual(genomeSize, minLimit, maxLimit, rand));
        }

        return population;

    }

    private Individual createRandomIndividual(int genomeSize, double minLimit, double maxLimit, Random rand) {

        double[] genome = new double[genomeSize];

        for (int i = 0; i < genomeSize; i++) {
            // Generate genotype
            genome[i] = ScaleToRange.scaleToRange(
                    rand.nextDouble(), 0.0,
                    1.0, minLimit, maxLimit);
        }

        return new Individual(genome, minLimit, maxLimit);
    }
}
