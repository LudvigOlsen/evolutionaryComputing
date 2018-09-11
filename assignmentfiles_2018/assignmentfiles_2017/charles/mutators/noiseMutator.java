package charles.mutators;

import charles.Individual;
import charles.utils.ScaleToRange;

import java.util.Random;

/**
 * Every genotype is multiplied by a (for each genotype) random double between -0.1 and 0.1.
 */
public class noiseMutator implements Mutator {
    private Random rand = new Random();

    @Override
    public void mutate(Individual individual) {

        double[] newGenome = new double[individual.getGenomeSize()];

        for (int gt = 0; gt < individual.getGenomeSize(); gt++) {

            // Between -0.1 and 0.1
            // Perhaps this should be decided from input params instead? E.g. pass a settings object?
            double noiseAmount = ScaleToRange.scaleToRange(
                    rand.nextDouble() / 5, 0.0,
                    0.2, -0.1, 0.1);

            newGenome[gt] = individual.getGenome()[gt] * noiseAmount;

        }

        individual.setGenome(newGenome);
    }
}
