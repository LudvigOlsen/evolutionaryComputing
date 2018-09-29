package charles.utils;

import java.util.Random;

public class DoubleMatrix2DGaussian extends DoubleMatrix2D {

    Random rand;

    public DoubleMatrix2DGaussian(int[] shape, Random rand) {
        super(shape);
        this.rand = rand;
        setMatrix(sampleFillFromGaussian());
    }

    private double[][] sampleFillFromGaussian() {
        double[][] newArray = new double[getShape()[0]][getShape()[1]];
        for (int i = 0; i < getShape()[0]; i++) {
            for (int j = 0; j < getShape()[1]; j++) {
                newArray[i][j] = rand.nextGaussian();
            }
        }
        return newArray;
    }
}
