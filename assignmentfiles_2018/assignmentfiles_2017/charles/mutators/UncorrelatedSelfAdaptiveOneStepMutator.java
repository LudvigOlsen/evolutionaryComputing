
package charles.mutators;

import charles.Individual;

import java.util.ArrayList;
import java.util.Random;

public class UncorrelatedSelfAdaptiveOneStepMutator implements Mutator {

    private Random rand;
    private double commonLearningRateMultiplier;

    public UncorrelatedSelfAdaptiveOneStepMutator(Random rand,
                                                  double commonLearningRateMultiplier) {
        this.rand = rand;
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
    }

    /**
     * Note: Genome array at index 1 must be the array of step sizes!
     * <p>
     * Only changes first 2 arrays in genome list.
     * <p>
     * When using this, the min limit for sigmas must be at least ~1E-6, while the max limit can be e.g. 2.
     *
     * @param individual
     */
    @Override
    public void mutate(Individual individual) {

        ArrayList<double[]> oldGenome = individual.getGenome();
        int representationSize = individual.getRepresentationSize();

        // Step size mutation parameters
        double commonLearningRate = commonLearningRateMultiplier * (1 / Math.sqrt(representationSize));

        double commonExponent = commonLearningRate * rand.nextGaussian();

        // Mutate step sizes
        double[] mutatedStepSize = mutateStepSize(oldGenome.get(1), commonExponent,
                individual.getMinLimits().get(1), individual.getMaxLimits().get(1));

        double[] mutatedRepresentation = mutateRepresentation(oldGenome.get(0), mutatedStepSize);

        oldGenome.set(0, mutatedRepresentation);
        oldGenome.set(1, mutatedStepSize);

        individual.setGenome(oldGenome);
    }

    private double[] mutateStepSize(double[] stepSizeArray, double commonExponent, double minLimit, double maxLimit) {

        stepSizeArray[0] = mutateSingleStepSize(stepSizeArray[0], commonExponent);

        return Individual.applyLimitsToDoubleArray(stepSizeArray, minLimit, maxLimit);

    }

    private double mutateSingleStepSize(double oldStepSize, double commonExponent) {
        return oldStepSize * Math.exp(commonExponent);
    }

    private double[] mutateRepresentation(double[] representationArray, double[] stepSizeArray) {

        double[] newGenomeArray = new double[representationArray.length];

        for (int gt = 0; gt < representationArray.length; gt++) {
            newGenomeArray[gt] = mutateSingleGenotype(representationArray[gt], stepSizeArray[0]);
        }

        return newGenomeArray;
    }

    private double mutateSingleGenotype(double oldGenotype, double stepSize) {
        return oldGenotype + stepSize * rand.nextGaussian();
    }

    public void setCommonLearningRateMultiplier(double commonLearningRateMultiplier) {
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
    }

}

