package charles;

import charles.utils.ScaleToRange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

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
    public void calculateGenomeProduct() {
        int representationSize = getIndividual(0).getRepresentationSize();
        ArrayList<BigDecimal> bigDecimalGenomeProduct = new ArrayList<>();
        double[] genomeProduct = new double[representationSize];

        // fill with 1's
        for (int gt = 0; gt < representationSize; gt++) {
            bigDecimalGenomeProduct.add(BigDecimal.valueOf(1.0));
        }
        // Rescale alleles to [0.0,1.0] and multiply with the same genotype (position) in the BigDecimal ArrayList 
        for (Individual individual : getPopulation()) {
            for (int gt = 0; gt < representationSize; gt++) {
                // Rescaling
                double rescaledGene = ScaleToRange.scaleToRange(individual.getRepresentation()[gt],
                        individual.getMinLimits().get(0), individual.getMaxLimits().get(0), 0.0, 1.0);
                bigDecimalGenomeProduct.set(gt, bigDecimalGenomeProduct.get(gt).multiply(
                        BigDecimal.valueOf(rescaledGene)));
            }
        }
        // Get sum for normalization
        BigDecimal sumOfGenomeProduct = BigDecimal.valueOf(0.0);
        for (int gt = 0; gt < representationSize; gt++) {
            sumOfGenomeProduct = sumOfGenomeProduct.add(bigDecimalGenomeProduct.get(gt));
        }

        // Normalize and add to genomeProduct 
        for (int gt = 0; gt < representationSize; gt++) {
            genomeProduct[gt] = bigDecimalGenomeProduct.get(gt).divide(sumOfGenomeProduct,
                    64, RoundingMode.HALF_UP).doubleValue();
        }

        this.genomeProduct = genomeProduct;
    }

    public double[] getGenomeProduct() {
        return genomeProduct;
    }


    /**
     * Calculate the (Product) Kuhlback Leibler Divergence between populations. (Possibly novel method).
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
    public double productKuhlbackLeiblerDivergence(Population otherPopulation) {
        int representationSize = getIndividual(0).getRepresentationSize();
        double[] othersGenomeProduct = otherPopulation.getGenomeProduct();

        double summation = 0;
        double epsilon = 1e-32; // NOTE: This should be relative to the representation size
        for (int gt = 0; gt < representationSize; gt++) {
            summation += Math.log(genomeProduct[gt] / (othersGenomeProduct[gt] + epsilon));
        }
        return summation / representationSize;
    }

}
