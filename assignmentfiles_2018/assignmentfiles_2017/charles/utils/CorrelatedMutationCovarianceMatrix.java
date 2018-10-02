package charles.utils;

/**
 * ------ -------- ----- NOT WORKING ------ -------- -----
 * <p>
 * Currently creates covariance matrices that are NOT positive semidefinite. Some error somewhere?
 */

public class CorrelatedMutationCovarianceMatrix extends DoubleMatrix2D {

    private DoubleMatrix2D signMatrix;

    public CorrelatedMutationCovarianceMatrix(int[] shape) {
        super(shape);
        signMatrix = new DoubleMatrix2D(shape);
    }

    public CorrelatedMutationCovarianceMatrix(double[][] arr) {
        super(arr);
        signMatrix = new DoubleMatrix2D(getShape());
    }

    public CorrelatedMutationCovarianceMatrix(double[] sigmas, double[] alphas) throws IllegalAccessException {

        super(new int[]{sigmas.length, sigmas.length}); // Initialize. We will overwrite it in a moment.
        signMatrix = new DoubleMatrix2D(getShape());
        update(sigmas, alphas);

        throw new IllegalAccessException("Currently, this creates non-positive definite matrices.");

    }

    public void update(double[] sigmas, double[] alphas) {
        double[][] covarianceArray = new double[sigmas.length][sigmas.length];

        int currentAlpha = 0;
        for (int i = 0; i < sigmas.length; i++) {
            for (int j = 0; j < sigmas.length; j++) {
                if (i == j) {
                    covarianceArray[i][j] = Math.pow(sigmas[i], 2);
                } else if (j > i) {
                    covarianceArray[i][j] = 0; // We'll copy it over later
                } else {
                    covarianceArray[i][j] = (1.0 / 2)
                            * (Math.pow(sigmas[i], 2) - Math.pow(sigmas[j], 2))
                            * Math.tan(2 * alphas[returnIfLessThan(currentAlpha, alphas.length)]);
                    currentAlpha++;
                }
            }
        }

        // Copy over symmetrical information and set matrix.
        setMatrix(copySymmetricalInformation(covarianceArray));
        this.setMatrix(covarianceArray);
        updateSignMatrix();
        //System.out.println(this);
        //throw new IllegalArgumentException("stop");
    }

    private int returnIfLessThan(int v, int lt) {
        if (v < lt) return v;
        else throw new IllegalArgumentException("v is not less than lt");
    }

    private double[][] copySymmetricalInformation(double[][] covarianceArray) {

        for (int i = 0; i < covarianceArray.length; i++) {
            for (int j = 0; j < covarianceArray.length; j++) {
                if (j > i) {
                    covarianceArray[i][j] = covarianceArray[j][i];
                }
            }

        }

        return covarianceArray;
    }

    private void updateSignMatrix() {
        for (int i = 0; i < getShape()[0]; i++) {
            for (int j = 0; j < getShape()[1]; j++) {
                if (getElement(i, j) < 0.0) signMatrix.setElement(i, j, -1.0);
                else signMatrix.setElement(i, j, 1.0);
            }
        }
    }

    public DoubleMatrix2D getSignMatrix() {
        return signMatrix;
    }


}