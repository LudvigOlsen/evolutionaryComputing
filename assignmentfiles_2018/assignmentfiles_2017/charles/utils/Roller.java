package charles.utils;

import java.util.ArrayList;

/**
 * Rolls an array by n indices.
 * I.e. [0,1,2,3] => [1,2,3,0]
 */
public class Roller {

    // TODO Write generic version

    /**
     * Roll double[] by n indices.
     *
     * @param arr Array to roll.
     * @param n   Number of indices to roll.
     * @return Rolled array.
     */
    public static double[] roll(double[] arr, int n) {

        double[] rolledArray = new double[arr.length];

        if (n == 0) rolledArray = arr;
        else {
            if (n < 0) n = arr.length + n; // [0,1,2,3] n == -1 => n = 4-1 = 3

            // First get the ones >= n
            int ind = 0;
            for (int i = n; i < arr.length; i++) {
                rolledArray[ind] = arr[i];
                ind++;
            }
            // Now get the ones < n
            for (int i = 0; i < n; i++) {
                rolledArray[ind] = arr[i];
                ind++;
            }
        }

        return rolledArray;

    }


    /**
     * Wrapper for rolling ArrayList of double arrays.
     * All double arrays are rolled with n.
     *
     * @param arr List of arrays to roll.
     * @param n   Number of indices to roll.
     * @return List of rolled arrays.
     */
    public static ArrayList<double[]> roll(ArrayList<double[]> arr, int n) {
        for (int i = 0; i < arr.size(); i++) {
            arr.set(i, roll(arr.get(i), n));
        }
        return arr;
    }

}
