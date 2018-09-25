package charles.utils;

public class DoubleMatrix2D implements Matrix {

    private int[] shape; // [row, col]
    private int longestDimension;
    private int shortestDimension;
    private double[][] matrix;

    public DoubleMatrix2D(double[][] arr) {
        allRowsHaveSameLength(arr);
        this.shape = inferShape(arr);
        this.matrix = arr;
        setLongestAndShortestDimension();
    }

    public DoubleMatrix2D(int[] shape) {
        this(new double[shape[0]][shape[1]]);
        assert shape.length == 2;
    }

    public int[] getShape() {
        return shape;
    }

    private int[] inferShape(double[][] arr) {
        return new int[]{arr.length, arr[0].length};
    }

    private void allRowsHaveSameLength(double[][] arr) {
        int firstRowsLength = arr[0].length;
        for (double[] row : arr) {
            assert row.length == firstRowsLength;
        }
    }

    public double[] getDiagonal() {
        double[] diagonal = new double[shortestDimension];
        for (int i = 0; i < shortestDimension; i++) {
            diagonal[i] = getElement(i, i);
        }
        return diagonal;
    }

    public DoubleMatrix2D transpose() {
        double[][] transposed = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                transposed[j][i] = matrix[i][j];
        return new DoubleMatrix2D(transposed);
    }

    public DoubleMatrix2D dot(DoubleMatrix2D otherMatrix) {
        assert shape[1] == otherMatrix.getShape()[0];
        double[][] product = new double[shape[0]][otherMatrix.getShape()[1]];
        for (int leftRow = 0; leftRow < shape[0]; leftRow++) {
            for (int rightCol = 0; rightCol < otherMatrix.getShape()[1]; rightCol++) {
                product[leftRow][rightCol] = sum(multiplyElementwise(getRow(leftRow), otherMatrix.getCol(rightCol)));
            }
        }
        return new DoubleMatrix2D(product);
    }

    private double[] multiplyElementwise(double[] arr1, double[] arr2) {
        assert arr1.length == arr2.length;
        double[] product = new double[arr1.length];
        for (int e = 0; e < arr1.length; e++) {
            product[e] = arr1[e] * arr2[e];
        }
        return product;
    }

    public DoubleMatrix2D multiplyElementwise(DoubleMatrix2D otherMatrix) {
        assert shape[0] == otherMatrix.getShape()[0] && shape[1] == otherMatrix.getShape()[1];
        double[][] product = new double[shape[0]][shape[1]];
        for (int row = 0; row < shape[0]; row++) {
            for (int col = 0; col < shape[1]; col++) {
                product[row][col] = matrix[row][col] * otherMatrix.getElement(row, col);
            }
        }
        return new DoubleMatrix2D(product);
    }

    public DoubleMatrix2D addElementwise(DoubleMatrix2D otherMatrix) { // TODO they share code. Make functional?
        assert shape[0] == otherMatrix.getShape()[0] && shape[1] == otherMatrix.getShape()[1];
        double[][] product = new double[shape[0]][shape[1]];
        for (int row = 0; row < shape[0]; row++) {
            for (int col = 0; col < shape[1]; col++) {
                product[row][col] = matrix[row][col] + otherMatrix.getElement(row, col);
            }
        }
        return new DoubleMatrix2D(product);
    }

    public DoubleMatrix2D multiplyWithScalar(double scalar) {
        double[][] product = new double[shape[0]][shape[1]];
        for (int row = 0; row < shape[0]; row++) {
            for (int col = 0; col < shape[1]; col++) {
                product[row][col] = matrix[row][col] * scalar;
            }
        }
        return new DoubleMatrix2D(product);
    }

    public DoubleMatrix2D addScalar(double scalar) {
        double[][] product = new double[shape[0]][shape[1]];
        for (int row = 0; row < shape[0]; row++) {
            for (int col = 0; col < shape[1]; col++) {
                product[row][col] = matrix[row][col] + scalar;
            }
        }
        return new DoubleMatrix2D(product);
    }

    private double sum(double[] arr) {
        double sum_ = 0;
        for (double e : arr) sum_ += e;
        return sum_;
    }

    public double[] getRow(int index) {
        return matrix[index];
    }

    public double[] getCol(int index) {
        double[] column = new double[shape[1]];
        for (int row = 0; row < shape[0]; row++) {
            column[row] = matrix[row][index];
        }
        return column;
    }
    
    public double getElement(int i, int j) {
        return matrix[i][j];
    }

    public void setElement(int i, int j, double value) {
        matrix[i][j] = value;
    }

    private void setLongestAndShortestDimension() {
        if (shape[0] < shape[1]) {
            longestDimension = 1;
            shortestDimension = 0;
        } else {
            longestDimension = 0;
            shortestDimension = 1;
        }
    }
}
