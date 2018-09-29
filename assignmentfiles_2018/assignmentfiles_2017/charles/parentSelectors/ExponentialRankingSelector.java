package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

import java.util.Collections;
import java.util.Random;

public class ExponentialRankingSelector implements ParentSelector {

    private Random rand;
    private WeightedSampler sampler;
    private Population parents;

    public ExponentialRankingSelector(Random rand) {
        this.rand = rand;
        sampler = new WeightedSampler(this.rand);
        parents = new Population();
    }

    @Override
    public Population selectParents(Population population, int numParents) {

        sampler.clearSampler(); // Remove previous mappings
        parents.clear();

        // Sort population by highest to lowest fitness
        Collections.sort(population.getPopulation());

        int popSize = population.getPopulationSize();
        double normalizationFactor = calculateNormalizationFactor(popSize);

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
            // 1E-5
            sampler.add(individual, calculateProbability(i, normalizationFactor));

        }

        for (int p = 0; p < numParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }

    /**
     * P_{exp-rank}(i) = (1-e^{-i}) / c
     * Where c = sum( (1-e^{-i}) for all i).
     * <p>
     * Introduction to Evolutionary Computing, 2nd ed., A.E. Eiben & J.E. Smith.
     *
     * @param i          Current rank.
     * @param normFactor The Normalization factor c  - ( sum( (1-e^{-i}) for all i) ).
     *                   This is calculated once per population to save computation.
     * @return Probability for rank i.
     */
    private double calculateProbability(int i, double normFactor) {
        return singleExponentiation(i) / normFactor;
    }

    private double calculateNormalizationFactor(int popSize) {
        double sum = 0;
        for (int i = 0; i < popSize; i++) {
            sum += singleExponentiation(i);
        }
        return sum;
    }

    private double singleExponentiation(int i) {
        return 1 - Math.exp(-i);
    }
}
