package charles.parentSelectors;

import charles.Individual;
import charles.Population;
import charles.utils.WeightedSampler;

import java.util.Collections;
import java.util.Random;

public class LinearRankingSelector implements ParentSelector {

    private Random rand;
    private WeightedSampler sampler;
    private Population parents;
    private double sParam;

    public LinearRankingSelector(Random rand, double sParam) {
        this.rand = rand;
        sampler = new WeightedSampler(this.rand);
        parents = new Population();
        if (sParam <= 1 || sParam > 2) throw new IllegalArgumentException("sParam must be > 1 and <= 2.");
        this.sParam = sParam;
    }

    @Override
    public Population selectParents(Population population, int numParents) {

        sampler.clearSampler(); // Remove previous mappings
        parents.clear();

        // Sort population by highest to lowest fitness
        Collections.sort(population.getPopulation());

        int popSize = population.getPopulationSize();

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
            // 1E-5
            sampler.add(individual, calculateProbability(popSize, i));

        }

        for (int p = 0; p < numParents; p++) {
            parents.addIndividual(sampler.next());
        }

        return parents;
    }

    /**
     * P_{lin-rank}(i) = (2-s) / μ + (2i(s-1)) / (μ(μ-1))
     * <p>
     * Introduction to Evolutionary Computing, 2nd ed., A.E. Eiben & J.E. Smith.
     *
     * @param popSize Population size.
     * @param i       Current rank.
     * @return Probability for rank i.
     */
    private double calculateProbability(int popSize, int i) {
        return ((2 - sParam) / (double) popSize)
                + ((2 * (double) i * (sParam - 1))
                / ((double) popSize * ((double) popSize - 1)));
    }

    public void setSParam(double sParam) {
        if (sParam <= 1 || sParam > 2) throw new IllegalArgumentException("sParam must be > 1 and <= 2.");
        this.sParam = sParam;
    }
}
