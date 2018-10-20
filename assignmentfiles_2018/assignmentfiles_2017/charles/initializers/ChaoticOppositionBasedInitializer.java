package charles.initializers;

import charles.Individual;
import charles.Population;
import charles.utils.ScaleToRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChaoticOppositionBasedInitializer implements Initializer {

    private Random rand;
    private int chaosFn;
    private int maxK = 555; // at least 300

    /**
     * @param rand Random object
     * @param maxK Number of times to iterate chaos function.
     */
    public ChaoticOppositionBasedInitializer(Random rand, int maxK) {
        this.rand = rand;
        if (maxK >= 300) this.maxK = maxK;
        else throw new IllegalArgumentException("maxK must be >= 300.");
    }


    private double getChaos() {
        if (chaosFn == 1) return rand.nextDouble();
        else if (chaosFn == 2) return chaosFromSineMap();
        else if (chaosFn == 3) return chaosFromCosineMap();
        else if (chaosFn == 4) return chaosFromLogisticMap();
        else if (chaosFn == 5) return chaosFromCubicMap();
        else
            throw new IllegalArgumentException("Only have 5 initializers for now, counting from 1. Please use setChaosFn().");
    }

    /**
     * Uses a chaos function for random initialization.
     * Creates half the members with the chaos functions, and
     * half by inverting the first half (opposition).
     * <p>
     * Chaos function:
     * Init ch as random number in [0,1]
     * ch = sin(PI * ch)  for K times
     *
     * @param numIndividuals Number of individuals.
     * @param genomeSizes    List of genome array sizes.
     * @param minLimits      List of minimum limits.
     * @param maxLimits      List of maximum limits.
     * @return Initialized Population.
     */
    public Population initialize(int numIndividuals,
                                 List<Integer> genomeSizes,
                                 List<Double> minLimits,
                                 List<Double> maxLimits) {

        Population population = new Population();

        for (int i = 0; i < (numIndividuals / 2); i++) {
            Individual newIndividual = createRandomIndividual(genomeSizes, minLimits, maxLimits);
            Individual oppositeIndividual = createOppositeIndividual(newIndividual);

            population.addIndividual(newIndividual);
            population.addIndividual(oppositeIndividual);
        }

        return population;
    }

    private Individual createRandomIndividual(List<Integer> genomeSizes,
                                              List<Double> minLimits,
                                              List<Double> maxLimits) {

        final ArrayList<double[]> newGenome = new ArrayList<>();

        for (int i = 0; i < genomeSizes.size(); i++) {
            newGenome.add(createSingleGenomeArray(genomeSizes.get(i), minLimits.get(i), maxLimits.get(i)));
        }

        return new Individual(newGenome, minLimits, maxLimits);
    }

    private Individual createOppositeIndividual(Individual individual) {

        final ArrayList<double[]> newGenome = new ArrayList<>();

        for (int i = 0; i < individual.getNumGenomeArrays(); i++) {
            newGenome.add(createSingleOppositeGenomeArray(individual.getGenome().get(i),
                    individual.getMinLimits().get(i), individual.getMaxLimits().get(i)));
        }

        return new Individual(newGenome, individual.getMinLimits(), individual.getMaxLimits());
    }

    private double[] createSingleOppositeGenomeArray(double[] genomeArray, Double minLimit, Double maxLimit) {
        double[] oppositeArray = new double[genomeArray.length];

        for (int g = 0; g < genomeArray.length; g++) {
            oppositeArray[g] = maxLimit + minLimit - genomeArray[g];
        }

        return oppositeArray;
    }

    private double[] createSingleGenomeArray(int genomeSize, double minLimit, double maxLimit) {
        double[] newGenomeArray = new double[genomeSize];

        for (int i = 0; i < genomeSize; i++) {
            // Generate genotype
            newGenomeArray[i] = ScaleToRange.scaleToRange(
                    getChaos(), 0.0,
                    1.0, minLimit, maxLimit);
        }

        return newGenomeArray;
    }

    private double chaosFromSineMap() {
        double chaos = rand.nextDouble();

        double my = 0.99;

        for (int k = 0; k < maxK; k++) {
            //feed random variable to the chaos function
            chaos = my * Math.sin(Math.PI * chaos);
        }

        return chaos;

    }

    private double chaosFromCosineMap() {
        double chaos = rand.nextDouble();

        double my = 0.99;

        for (int k = 0; k < maxK; k++) {
            //feed random variable to the chaos function
            chaos = my * Math.cos(Math.PI * Math.abs(chaos - 0.5));
        }

        return chaos;

    }

    private double chaosFromLogisticMap() {
        double chaos = rand.nextDouble();

        double my = 4.0;

        for (int k = 0; k < maxK; k++) {
            //feed random variable to the chaos function
            chaos = my * chaos * (1 - chaos);
        }

        return chaos;

    }

    private double chaosFromCubicMap() {
        double chaos = rand.nextDouble();

        double my = 2.59;

        for (int k = 0; k < maxK; k++) {
            //feed random variable to the chaos function
            chaos = my * chaos * (1 - Math.pow(chaos, 2));
        }

        return chaos;

    }


    public int getChaosFn() {
        return chaosFn;
    }

    public void setChaosFn(int chaosFn) {
        this.chaosFn = chaosFn;
    }
}

