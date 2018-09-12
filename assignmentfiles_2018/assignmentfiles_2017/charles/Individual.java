package charles;

import charles.utils.Numbers;

import java.text.DecimalFormat;

public class Individual implements Comparable<Individual> {

    // Counts instances of individuals created
    // Used for creating unique IDs
    private static int individualsCreatedCounter;

    private double genome[];
    private double minLimit;
    private double maxLimit;
    private double fitnessScore = 0.0;
    private Boolean wasEvaluatedFlag = false;
    private int age;
    private int id;

    public Individual(double[] genome, double minLimit, double maxLimit) {
        this.genome = genome;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        this.age = 0;

        individualsCreatedCounter++;
        this.id = individualsCreatedCounter;
    }

    public double[] getGenome() {
        return genome;
    }

    public void setGenome(double[] genome) {
        this.genome = applyLimitsToGenome(genome);
    }

    /*
    Makes sure that genome is within min and max limits.
    If larger than max limit => set to max limit.
    If smaller than min limit => set to min limit.
     */
    private double[] applyLimitsToGenome(double[] genome) {

        final double[] newGenome = new double[getGenomeSize()];
        for (int gt = 0; gt < getGenomeSize(); gt++) {
            if (genome[gt] > maxLimit) {
                newGenome[gt] = maxLimit;
            } else if (genome[gt] < minLimit) {
                newGenome[gt] = minLimit;
            } else {
                newGenome[gt] = genome[gt];
            }
        }
        return newGenome;
    }

    public int getGenomeSize() {
        return this.genome.length;
    }

    public void printGenome() {

        System.out.print("[");
        for (int g = 0; g < getGenome().length; g++) {
            System.out.print(Numbers.roundToNDecimals(getGenome()[g], 2)); // I'm not allowed to change formatting apparently
            if (g != getGenome().length - 1) System.out.print(", ");
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

}
