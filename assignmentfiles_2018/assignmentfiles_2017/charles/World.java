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
                diversitySum += islands.get(i).productKullbackLeiblerDivergence(islands.get(j));
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
}
