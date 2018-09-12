package charles.mutators;

import charles.Individual;
import charles.utils.ScaleToRange;

import java.util.Random;

/**
 * Every genotype is multiplied by a (for each genotype) random double between -0.1 and 0.1.
 */
public class NoiseMutator implements Mutator {
    private Random rand;
    private double amountMin, amountMax;

    public NoiseMutator(Random rand, double amountMin, double amountMax) {
        this.rand = rand;
        this.amountMin = amountMin;
        this.amountMax = amountMax;
    }

    public void setAmountMin(double amountMin) {
        this.amountMin = amountMin;
    }

    public void setAmountMax(double amountMax) {
        this.amountMin = amountMax;
    }


    @Override
    public void mutate(Individual individual) {

        double[] newGenome = new double[individual.getGenomeSize()];

        for (int gt = 0; gt < individual.getGenomeSize(); gt++) {

            // E.g. between -0.1 and 0.1
            // Perhaps this should be decided from input params instead? E.g. pass a settings object?
            double noiseAmount = ScaleToRange.scaleToRange(
                    rand.nextDouble(), 0.0, 1.0, amountMin, amountMax);

            double currentGenotype = individual.getGenome()[gt];
            newGenome[gt] = currentGenotype + (currentGenotype * noiseAmount);

        }

        individual.setGenome(newGenome);
    }
}
