package charles;

import charles.utils.Numbers;
import charles.utils.ScaleToRange;

import java.util.ArrayList;
import java.util.List;

public class Individual implements Comparable<Individual> {

    // Counts instances of individuals created
    // Used for creating unique IDs
    private static int individualsCreatedCounter;

    private ArrayList<double[]> genome;
    private double[] rescaledRepresentation; // the first array of genome in range [1e-6, 1.0]
    private List<Double> minLimits;
    private List<Double> maxLimits;
    private double fitnessScore = 0.0;
    private Boolean wasEvaluatedFlag = false;
    private int age;
    private int id;

    /**
     * @param genome    List of double arrays, where first double[] must always be the actual float representation
     *                  The other arrays may be parameters for self-adaption etc.
     * @param minLimits List of minimum limits, in the same order as the arrays in genome
     * @param maxLimits List of maximum limits, in the same order as the arrays in genome
     */
    public Individual(ArrayList<double[]> genome, List<Double> minLimits, List<Double> maxLimits) {
        this.minLimits = minLimits;
        this.maxLimits = maxLimits;
        this.age = 0;

        setGenome(genome); // Must be set after min and max limits

        individualsCreatedCounter++;
        this.id = individualsCreatedCounter;
    }

    /**
     * @return List of arrays where the first is the actual representation and the others are for self-adaptation etc.
     */
    public ArrayList<double[]> getGenome() {
        return genome;
    }

    /**
     * @return The representation for the evaluate() function.
     */
    public double[] getRepresentation() {
        return getGenome().get(0);
    }

    public void setGenome(ArrayList<double[]> genome) {
        this.genome = applyLimitsToGenome(genome);
        updateRescaledRepresentation();
    }

    /*
    Makes sure that genome is within min and max limits.
    If larger than max limit => set to max limit.
    If smaller than min limit => set to min limit.
     */
    private ArrayList<double[]> applyLimitsToGenome(ArrayList<double[]> genome) {

        // Initialize new array
        final ArrayList<double[]> newGenome = new ArrayList<>();

        // Apply limits to each genome array
        for (int a = 0; a < genome.size(); a++) {
            newGenome.add(applyLimitsToDoubleArray(genome.get(a), minLimits.get(a), maxLimits.get(a)));
        }

        return newGenome;
    }

    public static double[] applyLimitsToDoubleArray(double[] arr, double minLimit, double maxLimit) {
        final double[] newArray = new double[arr.length];
        for (int gt = 0; gt < arr.length; gt++) {
            if (arr[gt] > maxLimit) {
                newArray[gt] = maxLimit;
            } else if (arr[gt] < minLimit) {
                newArray[gt] = minLimit;
            } else {
                newArray[gt] = arr[gt];
            }
        }
        return newArray;
    }

    public int getRepresentationSize() {
        return getRepresentation().length;
    }

    public int getGenomeArraySize(int arrayIndex) {
        return getGenome().get(arrayIndex).length;
    }

    public int getNumGenomeArrays() {
        return getGenome().size();
    }

    private void updateRescaledRepresentation() {

        double[] rescaledRepresentation = new double[genome.get(0).length];

        for (int gt = 0; gt < genome.get(0).length; gt++) {
            // Rescaling
            rescaledRepresentation[gt] = ScaleToRange.scaleToRange(getRepresentation()[gt],
                    getMinLimits().get(0), getMaxLimits().get(0), 1e-6, 1.0);
        }

        this.rescaledRepresentation = rescaledRepresentation;
    }

    public void printRepresentation() {
        printGenomeArray(0);
    }

    public void printGenomeArray(int genomeArrayIndex) {
        printGenomeArray(getGenome().get(genomeArrayIndex));
    }

    public static void printGenomeArray(double[] genomeArray) {
        System.out.print("[");
        for (int g = 0; g < genomeArray.length; g++) {
            System.out.print(Numbers.roundToNDecimals(genomeArray[g], 2)); // I'm not allowed to change formatting apparently
            if (g != genomeArray.length - 1) System.out.print(", ");
            else System.out.println("]");
        }
    }


    public void setFitnessScore(double score) {
        fitnessScore = score;
        wasEvaluatedFlag = true;

    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age++;
    }

    public int getId() {
        return id;
    }

    public Boolean wasEvaluated() {
        return wasEvaluatedFlag;
    }

    public int compareTo(Individual individual) {
        return Double.compare(fitnessScore, individual.getFitnessScore());
    }

    public List<Double> getMinLimits() {
        return minLimits;
    }

    public void setMinLimits(List<Double> minLimits) {
        this.minLimits = minLimits;
    }

    public List<Double> getMaxLimits() {
        return maxLimits;
    }

    public void setMaxLimits(List<Double> maxLimits) {
        this.maxLimits = maxLimits;
    }

    public double[] getRescaledRepresentation() {
        return rescaledRepresentation;
    }
}
