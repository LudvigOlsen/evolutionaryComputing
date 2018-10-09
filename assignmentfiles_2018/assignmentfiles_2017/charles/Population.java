package charles;

import charles.utils.ScaleToRange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static charles.Individual.printGenomeArray;

public class Population {

    private ArrayList<Individual> population;
    private double totalFitnessScore = 0.0;
    private double[] genomeProduct;

    public Population() {
        population = new ArrayList<>();
    }

    public void addIndividual(Individual C) {
        population.add(C);
    }

    public void deleteIndividual(int index) {
        population.remove(index);
    }

    public void deleteIndividual(Individual C) {
        population.remove(C);
    }

    public void subtract(Population otherPopulation) {
        for (Individual individual : otherPopulation.getPopulation()) {
            deleteIndividual(individual);
        }
    }

    public void clear() {
        population.clear();
        totalFitnessScore = 0.0;
    }

    public Individual getIndividual(int index) {
        return population.get(index);
    }

    public int getPopulationSize() {
        return population.size();
    }

    /*
    Returns the population ArrayList<Individual>.
     */
    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * Merge current population with another population
     *
     * @param population The population to add to the current population.
     */
    public void merge(Population population) {
        this.population.addAll(population.getPopulation());
    }

    /**
     * Returns subset with the individuals at the given indices.
     *
     * @param indices
     * @return Subpopulation.
     */
    public Population subset(ArrayList<Integer> indices) {
        Population subpopulation = new Population();

        for (int p = 0; p < getPopulationSize(); p++) {
            if (indices.contains(p)) {
                subpopulation.addIndividual(this.population.get(p));
            }
        }
        return subpopulation;
    }

    /**
     * Pops the individuals at the given indices
     *
     * @param indices
     * @return
     */
    public Population pullOut(ArrayList<Integer> indices) {
        Population subpopulation = new Population();

        for (int p = 0; p < getPopulationSize(); p++) {
            if (indices.contains(p)) {
                subpopulation.addIndividual(this.population.get(p));
            }
        }

        // Delete members of the subpopulation from this.population
        subtract(subpopulation);

        return subpopulation;
    }


    private void calculateTotalFitnessScore() {
        double totalFitnessScore = 0.0;
        for (Individual individual : population) {
            totalFitnessScore += individual.getFitnessScore();
        }

        this.totalFitnessScore = totalFitnessScore;

    }

    /**
     * Calculates and return total fitness score for population.
     */
    public double getTotalFitnessScore() {
        calculateTotalFitnessScore();
        return totalFitnessScore;
    }

    public double getAverageFitnessScore() {
        calculateTotalFitnessScore();
        return totalFitnessScore / getPopulationSize();
    }

    /**
     * Multiply all the genes together, such that the gene 1 for each individual is multiplied with each other, etc.
     * The product array, with the same size as the individual's genome, is normalized product to sum to 1.0.
     */
    public void calculateGenomeProduct(int numIndividualsAtATime) {
        int representationSize = getIndividual(0).getRepresentationSize();

        ArrayList<BigDecimal> bigDecimalGenomeProduct = new ArrayList<>();
        double[] genomeProduct = new double[representationSize];

        // fill with 1's
        for (int gt = 0; gt < representationSize; gt++) {
            bigDecimalGenomeProduct.add(BigDecimal.valueOf(1.0));
        }

        for (int s = 0; s < Math.ceil(getPopulationSize() / (float) numIndividualsAtATime); s++) {
            ArrayList<Integer> indices = new ArrayList<>();

            for (int i = s * numIndividualsAtATime; i < (s + 1) * numIndividualsAtATime; i++) {
                if (i > getPopulationSize()) break;
                indices.add(i);
            }

            Population subpopulation = subset(indices);
            ArrayList<BigDecimal> subpopGenomeProduct = calculateSubpopulationGenomeProduct(subpopulation);

            // Product of subpopulation genome product and population genome product
            for (int gt = 0; gt < representationSize; gt++) {
                bigDecimalGenomeProduct.set(gt, bigDecimalGenomeProduct.get(gt).multiply(
                        subpopGenomeProduct.get(gt)));
            }

        }

        normalizeGenomeProduct(bigDecimalGenomeProduct, representationSize);

        // Convert to double[]
        for (int gt = 0; gt < representationSize; gt++) {
            genomeProduct[gt] = bigDecimalGenomeProduct.get(gt).doubleValue();
        }

        this.genomeProduct = genomeProduct;
    }

    private ArrayList<BigDecimal> calculateSubpopulationGenomeProduct(Population subpopulation) {
        int representationSize = getIndividual(0).getRepresentationSize();
        ArrayList<BigDecimal> bigDecimalGenomeProduct = new ArrayList<>();

        // Fill with 1's
        for (int gt = 0; gt < representationSize; gt++) {
            bigDecimalGenomeProduct.add(BigDecimal.valueOf(1.0));
        }

        // Multiply the genotype (rescaled to [1e-6, 1.0]) with the same genotype (position) in the BigDecimal ArrayList 
        for (Individual individual : subpopulation.getPopulation()) {
            for (int gt = 0; gt < representationSize; gt++) {
                bigDecimalGenomeProduct.set(gt, bigDecimalGenomeProduct.get(gt).multiply(
                        BigDecimal.valueOf(individual.getRescaledRepresentation()[gt])));
            }
        }

        // Normalize 
        normalizeGenomeProduct(bigDecimalGenomeProduct, representationSize);

        return bigDecimalGenomeProduct;
    }

    private BigDecimal calculateSumOfGenomeProduct(ArrayList<BigDecimal> bigDecimalGenomeProduct,
                                                   int representationSize) {
        BigDecimal sumOfGenomeProduct = BigDecimal.valueOf(0.0);
        for (int gt = 0; gt < representationSize; gt++) {
            sumOfGenomeProduct = sumOfGenomeProduct.add(bigDecimalGenomeProduct.get(gt));
        }

        return sumOfGenomeProduct;
    }

    private void normalizeGenomeProduct(ArrayList<BigDecimal> bigDecimalGenomeProduct,
                                        int representationSize) {

        // Get sum for normalization
        BigDecimal sumOfGenomeProduct = calculateSumOfGenomeProduct(bigDecimalGenomeProduct, representationSize);

        for (int gt = 0; gt < representationSize; gt++) {
            bigDecimalGenomeProduct.set(gt, bigDecimalGenomeProduct.get(gt).divide(sumOfGenomeProduct,
                    256, RoundingMode.HALF_UP));
        }

    }


    public double[] getGenomeProduct() {
        return genomeProduct;
    }


    /**
     * Calculate the absolute (Product) Kullback Leibler Divergence between populations. (Possibly novel method).
     * <p>
     * The product of all individuals in a population,
     * such that for all genome positions g in genome product GP and individual i,
     * the corresponding allele values of the individuals' genomes G are multiplied:
     * GP_g = π_[i=1..n](G_[i,g])
     * The genome product is normalized to sum to 1.0.
     * <p>
     * E.g.:
     * individual 1:   [0.5,  0.2,  0.3].
     * individual 2:   [0.5,  0.2,  0.2].
     * genome product: [0.25, 0.04, 0.06] normalized to [0,71, 0,11, 0,17].
     *
     * @param otherPopulation A population to find the divergence from.
     * @return Kuhlback Leibler divergence of the genome products of the two populations.
     */
    public double absProductKullbackLeiblerDivergence(Population otherPopulation) {
        int representationSize = getIndividual(0).getRepresentationSize();
        double[] othersGenomeProduct = otherPopulation.getGenomeProduct();

        double summation = 0;
        double epsilon = 1e-32; // NOTE: This should be relative to the representation size
        for (int gt = 0; gt < representationSize; gt++) {
            summation += Math.abs(Math.log(genomeProduct[gt] / (othersGenomeProduct[gt] + epsilon)));
        }
        return summation / representationSize;
    }

}
