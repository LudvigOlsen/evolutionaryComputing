package charles.mutators;

import charles.Individual;

import java.util.ArrayList;
import java.util.Random;

public class UncorrelatedSelfAdaptiveMutator implements Mutator {

    private Random rand;
    private double commonLearningRateMultiplier, coordinateSpecificLearningRateMultiplier;

    public UncorrelatedSelfAdaptiveMutator(Random rand,
                                           double commonLearningRateMultiplier,
                                           double coordinateSpecificLearningRateMultiplier) {
        this.rand = rand;
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
        this.coordinateSpecificLearningRateMultiplier = coordinateSpecificLearningRateMultiplier;
    }

    /**
     * Note: Genome array at index 1 must be the array of step sizes!
     * <p>
     * Only changes first 2 arrays in genome list.
     * <p>
     * When using this, the min limit must be at least ~1E-6, while the max limit can be e.g. 2.
     *
     * @param individual
     */
    @Override
    public void mutate(Individual individual) {

        ArrayList<double[]> oldGenome = individual.getGenome();
        int representationSize = individual.getRepresentationSize();

        // Step size mutation parameters
        double commonLearningRate = commonLearningRateMultiplier * (1 / Math.sqrt(2 * representationSize));
        double coordinateSpecificLearningRate = coordinateSpecificLearningRateMultiplier
                * (1 / Math.sqrt(2 * Math.sqrt(representationSize)));
        double commonExponent = commonLearningRate * rand.nextGaussian();

        // Mutate step sizes
        double[] mutatedStepSizes = mutateStepSizes(oldGenome.get(1), commonExponent,
                coordinateSpecificLearningRate);

        double[] mutatedRepresentation = mutateRepresentation(oldGenome.get(0), mutatedStepSizes);

        oldGenome.set(0, mutatedRepresentation);
        oldGenome.set(1, mutatedStepSizes);

        individual.setGenome(oldGenome);
    }

    private double[] mutateStepSizes(double[] stepSizeArray, double commonExponent, double coordinateSpecificLearningRate) {

        double[] newGenomeArray = new double[stepSizeArray.length];

        for (int gt = 0; gt < stepSizeArray.length; gt++) {
            newGenomeArray[gt] = mutateSingleStepSize(stepSizeArray[gt], commonExponent, coordinateSpecificLearningRate);
        }

        return newGenomeArray;
    }

    private double mutateSingleStepSize(double oldStepSize, double commonExponent, double coordinateSpecificLearningRate) {
        return oldStepSize * Math.exp(commonExponent + coordinateSpecificLearningRate * rand.nextGaussian());
    }

    private double[] mutateRepresentation(double[] representationArray, double[] stepSizeArray) {

        double[] newGenomeArray = new double[representationArray.length];

        for (int gt = 0; gt < representationArray.length; gt++) {
            newGenomeArray[gt] = mutateSingleGenotype(representationArray[gt], stepSizeArray[gt]);
        }

        return newGenomeArray;
    }

    private double mutateSingleGenotype(double oldGenotype, double stepSize) {
        return oldGenotype + stepSize * rand.nextGaussian();
    }

    public void setCommonLearningRateMultiplier(double commonLearningRateMultiplier) {
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
    }

    public void setCoordinateSpecificLearningRateMultiplier(double coordinateSpecificLearningRateMultiplier) {
        this.coordinateSpecificLearningRateMultiplier = coordinateSpecificLearningRateMultiplier;
    }
}

