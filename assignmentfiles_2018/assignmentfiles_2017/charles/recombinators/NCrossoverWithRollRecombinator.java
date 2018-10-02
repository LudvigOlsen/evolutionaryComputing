package charles.recombinators;

import charles.Individual;
import charles.Population;
import charles.utils.Roller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NCrossoverWithRollRecombinator implements Recombinator {

    private Random rand;
    private NCrossoverRecombinator nCrossoverRecombinator;

    public NCrossoverWithRollRecombinator(Random rand) {
        this.rand = rand;
        nCrossoverRecombinator = new NCrossoverRecombinator(rand);
    }


    /**
     * Rolls parent a random number of indices.
     * Uses NCrossoverRecombinator.
     * Rolls back child and parents the same number of indices as parents were first rolled with.
     * <p>
     * Why?: When using n-crossover there's a positional bias, where the first and last element
     * is deterministically together/separate depending on whether n is even or odd. By randomly
     * rolling arrays previous to recombination, this is avoided.
     *
     * @param parents       Population of parents.
     * @param numCrossovers Number of crossover points.
     * @param minLimits     List of minimum limits of genotypes - one limit per genome array.
     * @param maxLimits     List of maximum limits of genotypes - one limit per genome array.
     * @return Child.
     */
    @Override
    public Individual combine(Population parents, int numCrossovers, List<Double> minLimits, List<Double> maxLimits) {
        int genomeSize = parents.getIndividual(0).getRepresentationSize();

        int indicesToRoll = (int) Math.round(rand.nextDouble() * (genomeSize - 2)) + 1;

        // Roll parents
        rollParents(parents, indicesToRoll);

        Individual child = nCrossoverRecombinator.combine(parents, numCrossovers, minLimits, maxLimits);

        // Roll back parents (references to individuals - not a copy/clone)
        rollParents(parents, -indicesToRoll);

        // Roll back child
        rollChild(child, -indicesToRoll);

        return child;

    }

    private void rollParents(Population parents, int indicesToRoll) {
        for (int p = 0; p < parents.getPopulationSize(); p++) {
            parents.getIndividual(p).setGenome(Roller.roll(parents.getIndividual(p).getGenome(), indicesToRoll));
        }
    }

    private void rollChild(Individual child, int indicesToRoll) {
        child.setGenome(Roller.roll(child.getGenome(), indicesToRoll));
    }

}
