package charles.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numbers {

    /**
     * Round double to n decimal places.
     * <p>
     * Note: This is used, as the ConsertTestBox.java won't allow us to use decimal formatting
     * due to some privileged properties.
     *
     * @param value the number
     * @param n     decimal places
     * @return the rounded number
     */
    public static double roundToNDecimals(double value, int n) {
        if (n < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(n, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /**
     * Round the decimals in science notation to n decimal places.
     * <p>
     * If value is not in science notation, value is rounded to n decimal places.
     * <p>
     * How:
     * Multiplies value by 10^(numInitialZeroes), rounds n decimals, divides by 10^(numInitialZeroes)
     * Value is converted to BigDecimal for precision but returned as double.
     * <p>
     * Note: This is used, as the ConsertTestBox.java won't allow us to use decimal formatting
     * due to some privileged properties.
     *
     * @param value the number
     * @param n     decimal places
     * @return the rounded number
     */
    public static double roundScienceNotationToNDecimals(double value, int n) {
        if (n < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        double newDouble;

        if (bd.compareTo(BigDecimal.ZERO) != 0 && value > -1.0 / (n * 10) && value < 1.0 / (n * 10)) {

            int numZeroDecimals = getNumSpecificInitialDecimals(value, 0);

            double multiplier = Math.pow(10, numZeroDecimals);
            BigDecimal multiplierBD = new BigDecimal(multiplier);

            bd = bd.multiply(multiplierBD);
            bd = bd.setScale(n, RoundingMode.HALF_UP);
            bd = bd.divide(multiplierBD, 32, RoundingMode.HALF_UP);
            newDouble = bd.doubleValue();

        } else {
            bd = bd.setScale(n, RoundingMode.HALF_UP);
            newDouble = bd.doubleValue();
        }

        return newDouble;
    }

    /**
     * Finds the number of times a specific decimal is repeated before another number occurs.
     * E.g. 0.000132, have 3 0's before the first non-zero number "1".
     */
    public static int getNumSpecificInitialDecimals(double value, int decimalValue) {
        BigDecimal bD = new BigDecimal(value);

        // split in whole number and decimals
        String[] parts = bD.toPlainString().split("\\.");
        String decimalPart = parts[1];

        // Find number of 0-decimals until first non-zero decimal
        int numZeroDecimals = 0;
        while (decimalPart.charAt(numZeroDecimals) == Double.toString(decimalValue).charAt(0))
            numZeroDecimals++;

        return numZeroDecimals;
    }

    public static int roundToInt(double val) {
        return (int) Math.round(val);
    }
}





