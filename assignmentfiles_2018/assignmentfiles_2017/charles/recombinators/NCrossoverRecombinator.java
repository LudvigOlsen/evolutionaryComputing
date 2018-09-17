package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NCrossoverRecombinator implements Recombinator {

    private Random rand;

    public NCrossoverRecombinator(Random rand) {
        this.rand = rand;
    }


    @Override
    public Individual combine(Population parents, int numCrossovers, double minLimit, double maxLimit) {
        int genomeSize = parents.getIndividual(0).getGenomeSize();
        int numParents = parents.getPopulationSize();

        assert genomeSize >= numParents;

        double[] childGenome = new double[genomeSize];

        // Get crossover indices by sampling without replacement from list of all possible indices

        ArrayList<Integer> possibleCrossoverIndices = new ArrayList<>();
        for (int i = 1; i < genomeSize - 1; i++) {
            possibleCrossoverIndices.add(i);
        }
        Collections.shuffle(possibleCrossoverIndices, rand);
        ArrayList<Integer> crossoverIndices = new ArrayList<>(possibleCrossoverIndices.subList(0, numCrossovers - 1));

        // Shuffle parents
        Collections.shuffle(parents.getPopulation(), rand);

        // Recombine genomes
        int currentParent = 0;
        for (int gt = 0; gt < genomeSize; gt++) {
            // Change parent if at a crossover point
            if (crossoverIndices.contains(gt)) currentParent = incrementParent(currentParent, numParents);
            childGenome[gt] = parents.getIndividual(currentParent).getGenome()[gt];
        }

        return new Individual(childGenome, minLimit, maxLimit);
    }

    private int incrementParent(int currentParent, int numParents) {
        if (currentParent == numParents-1) return 0;
        else return currentParent + 1;
    }

}
