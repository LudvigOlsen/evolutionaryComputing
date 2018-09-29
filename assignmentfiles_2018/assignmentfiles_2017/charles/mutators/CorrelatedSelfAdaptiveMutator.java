package charles.mutators;

import charles.Individual;
import charles.utils.CorrelatedMutationCovarianceMatrix;
import charles.utils.DoubleMatrix2D;
import charles.utils.DoubleMatrix2DGaussian;

import java.util.ArrayList;
import java.util.Random;

import static charles.utils.CholeskyDecomposition.applyCholesky;
import static charles.utils.DoubleMatrix2D.sum;

/**
 * ------ -------- ----- NOT WORKING YET ------ -------- -----
 * <p>
 * Currently covariance matrix function creates matrices that are NOT positive semidefinite. Some error somewhere?
 */

public class CorrelatedSelfAdaptiveMutator implements Mutator {

    private Random rand;
    private double commonLearningRateMultiplier, coordinateSpecificLearningRateMultiplier, rotationDegrees;
    private CorrelatedMutationCovarianceMatrix covarianceMatrix;
    private DoubleMatrix2D gaussianMatrix;

    public CorrelatedSelfAdaptiveMutator(Random rand,
                                         double commonLearningRateMultiplier,
                                         double coordinateSpecificLearningRateMultiplier,
                                         double rotationDegrees) {
        this.rand = rand;
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
        this.coordinateSpecificLearningRateMultiplier = coordinateSpecificLearningRateMultiplier;
        this.rotationDegrees = rotationDegrees;
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

        // Mutate alphas (rotation degrees)
        double[] mutatedAlphas = mutateAlphas(oldGenome.get(2), degreesToRadians(rotationDegrees));

//        covarianceMatrix = new CorrelatedMutationCovarianceMatrix(mutatedStepSizes, mutatedAlphas);
//        System.out.println(covarianceMatrix);
//        double[][] choleskyDecomposition = applyCholesky(covarianceMatrix.getDoubleArray());
//        System.out.println(new DoubleMatrix2D(choleskyDecomposition));

        // TODO Draw new samples from Gaussian nxn space with covariance matrix 
        // TODO and add to old genome
        // Currently the covariance matrix does not work

        double[] mutatedRepresentation = mutateRepresentation(oldGenome.get(0));

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

    /**
     * @param beta Given in radians!
     */
    private double mutateSingleAlpha(double oldAlpha, double beta) {
        return limitAlphaCircularly(oldAlpha + beta * rand.nextGaussian());
    }

    /**
     * @param beta Given in radians!
     */
    private double[] mutateAlphas(double[] alphas, double beta) {

        double[] newGenomeArray = new double[alphas.length];

        for (int gt = 0; gt < alphas.length; gt++) {
            newGenomeArray[gt] = mutateSingleAlpha(alphas[gt], beta);
        }

        return newGenomeArray;
    }


    private double[] mutateRepresentation(double[] representationArray) {

        double[] newGenomeArray = new double[representationArray.length];

        gaussianMatrix = new DoubleMatrix2DGaussian(covarianceMatrix.getShape(), rand);
        gaussianMatrix = gaussianMatrix.multiplyElementwise(covarianceMatrix);
        gaussianMatrix = gaussianMatrix.add1DVectorUsingBroadcasting(representationArray);
        // TODO STILL A MATRIX? Should I use cholesky decomposition? Should I just average columns? What to do?
        for (int col = 0; col < gaussianMatrix.getShape()[1]; col++) {
            newGenomeArray[col] = sum(gaussianMatrix.getCol(col)) / gaussianMatrix.getShape()[0];
        }
        return newGenomeArray;
    }

//    private double mutateSingleGenotype(double oldGenotype, double stepSize) {
//        return oldGenotype + stepSize * rand.nextGaussian();
//    }

    public void setCommonLearningRateMultiplier(double commonLearningRateMultiplier) {
        this.commonLearningRateMultiplier = commonLearningRateMultiplier;
    }

    public void setCoordinateSpecificLearningRateMultiplier(double coordinateSpecificLearningRateMultiplier) {
        this.coordinateSpecificLearningRateMultiplier = coordinateSpecificLearningRateMultiplier;
    }

    public static int calculateNumAlphas(int n) {
        return n * (n - 1) / 2;
    }

    private double limitAlphaCircularly(double alpha) {
        if (Math.abs(alpha) > Math.PI)
            alpha -= 2 * Math.PI * Math.signum(alpha);
        return alpha;
    }

    private double[] limitAlphasCircularly(final double[] alphas) {
        for (int a = 0; a < alphas.length; a++) {
            alphas[a] = limitAlphaCircularly(alphas[a]);
        }
        return alphas;
    }

    private double degreesToRadians(double degrees) {
        // 1° = π/180°
        return Math.PI / 180 * degrees;
    }
}

