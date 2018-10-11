package charles;

import java.util.ArrayList;

/**
 * Methods for describing the world, such as the inter-population diversity
 */
public class World {

    /**
     * Calculates the product Kullback Leibler Divergence between all populations and
     * returns the average comparison divergence.
     *
     * @param islands List of populations
     * @return Average comparison product Kullback Leibler Divergence.
     */
    public static double calculateInterPopulationDiversity(ArrayList<Population> islands) {

        // Calculate Genome Products
        for (Population island : islands) {
            island.calculateGenomeProduct(10);
        }

        // Calculate the product Kullback Leibler Divergence
        double diversitySum = 0;
        int numComparisons = 0; // should become same as factorial of islands.size().
        for (int i = 0; i < islands.size() - 1; i++) {
            for (int j = i + 1; j < islands.size(); j++) {
                diversitySum += islands.get(i).absProductKullbackLeiblerDivergence(islands.get(j));
                numComparisons++;
            }
        }
        return diversitySum / numComparisons;

    }

    public static double getAverageFitness(ArrayList<Population> islands) {
        double fitnessSum = 0;

        for (Population island : islands) {
            fitnessSum += island.getAverageFitnessScore();
        }

        return fitnessSum / islands.size();

    }

    /**
     * Calculates global diversity.
     * The diversity is the average of the standard deviations of all genes across all individuals.
     *
     * @param islands List of islands (populations)
     * @return Global (Standard Deviation) Diversity.
     */
    public static double calculateGlobalStdDiversity(ArrayList<Population> islands) {

        // For every gene, find the standard deviation of all individuals
        // Average the diversity 

        int representationSize = islands.get(0).getIndividual(0).getRepresentationSize();

        int totalNumIndividuals = 0;
        for (Population island : islands) {
            totalNumIndividuals += island.getPopulationSize();
        }

        double stdSum = 0.0;

        for (int g = 0; g < representationSize; g++) {

            // Find total sum of alleles_g 
            double alleleSum = 0.0;
            for (Population island : islands) {
                for (int i = 0; i < island.getPopulationSize(); i++) {
                    alleleSum += island.getIndividual(i).getGenome().get(0)[g];
                }
            }

            double alleleAverage = alleleSum / (totalNumIndividuals);

            // Calculate inner term: sum( (x_i - mean(x))^2 ) 
            double sumInner = 0;
            for (Population island : islands) {
                for (int i = 0; i < island.getPopulationSize(); i++) {
                    sumInner += Math.pow(island.getIndividual(i).getGenome().get(0)[g] - alleleAverage, 2);
                }
            }

            // Square root of average sum: sqrt( 1/N *  inner_term)
            double std = Math.sqrt(sumInner / totalNumIndividuals);
            stdSum += std;

        }

        // Return average allele standard deviation
        // This is the global diversity measure
        return stdSum / representationSize;

    }

    /**
     * Calculates average global fitness.
     *
     * @param islands List of islands (populations)
     * @return Average Global Fitness.
     */
    public static double calculateAverageGlobalFitness(ArrayList<Population> islands) {
        
        int totalNumIndividuals = 0;
        for (Population island : islands) {
            totalNumIndividuals += island.getPopulationSize();
        }

        double fitnessSum = 0.0;

        for (Population island : islands) {
            for (int i = 0; i < island.getPopulationSize(); i++) {
                fitnessSum += island.getIndividual(i).getFitnessScore();
            }
        }

        // Return average global fitness
        return fitnessSum / totalNumIndividuals;

    }
}
