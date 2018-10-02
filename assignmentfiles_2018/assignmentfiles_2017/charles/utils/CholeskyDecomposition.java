package charles.utils;


/**
 * Originally based on https://introcs.cs.princeton.edu/java/95linear/Cholesky.java.html
 * Core methods were reimplemented for educational purposes
 */
public class CholeskyDecomposition {

    public static boolean isSymmetric(double[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i][j] != arr[j][i]) return false;
            }
        }

        return true;
    }

    public static boolean isSquare(double[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != arr.length) return false;
        }

        return true;
    }


    /**
     * @param arr
     * @return Cholesky factor L of positive-definite matrix A = L*L^T
     */
    public static double[][] applyCholesky(double[][] arr) {                           // TODO UNDERSTAND!!!

        if (!isSquare(arr)) {
            throw new RuntimeException("Matrix must be square!");
        }
        if (!isSymmetric(arr)) {
            throw new RuntimeException("Matrix must be symmetric!");
        }

        double[][] L = new double[arr.length][arr.length];

        // row is k in the formula
        for (int row = 0; row < arr.length; row++) {
            // col is is i
            for (int col = 0; col <= row; col++) { // The decomposition only uses the triangle left of the diagonal
                if (row == col) {
                    L[row][col] = Math.sqrt(arr[row][col] - sumWhenRowIsCol(L, row));
                } else {
                    L[row][col] = (arr[row][col] - sumWhenRowIsNotCol(L, row, col)) / L[col][col];
                }
                if (L[col][col] <= 0) { // TODO Is this right?
                    throw new RuntimeException("Matrix not positive definite");
                }
            }
        }

        return L;
    }

    private static double sumWhenRowIsCol(double[][] L, int row) {
        double sum = 0;
        for (int j = 0; j < (row - 1); j++) {
            sum += Math.pow(L[row][j], 2);
        }
        return sum;
    }

    private static double sumWhenRowIsNotCol(double[][] L, int row, int col) {
        double sum = 0;
        for (int j = 0; j < (col - 1); j++) {
            sum += L[col][j] * L[row][j];
        }
        return sum;

    }

}
