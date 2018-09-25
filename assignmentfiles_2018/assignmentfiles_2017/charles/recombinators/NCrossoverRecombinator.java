package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NCrossoverRecombinator implements Recombinator {

    private Random rand;

    public NCrossoverRecombinator(Random rand) {
        this.rand = rand;
    }


    @Override
    public Individual combine(Population parents, int numCrossovers,
                              List<Double> minLimits, List<Double> maxLimits) {

        int numParents = parents.getPopulationSize();
        ArrayList<double[]> childGenome = new ArrayList<>();

        assert parents.getIndividual(0).getRepresentationSize() >= numParents;

        // Shuffle parents
        Collections.shuffle(parents.getPopulation(), rand);

        for (int g = 0; g < parents.getIndividual(0).getNumGenomeArrays(); g++) {
            childGenome.add(combineSingleGenomeArray(parents, g,
                    parents.getIndividual(0).getGenomeArraySize(g), numParents, numCrossovers));
        }

        return new Individual(childGenome, minLimits, maxLimits);
    }

    private int incrementParent(int currentParent, int numParents) {
        if (currentParent == numParents - 1) return 0;
        else return currentParent + 1;
    }

    private double[] combineSingleGenomeArray(Population parents, int genomeArrayIndex, int genomeSize,
                                              int numParents, int numCrossovers) {

        // Get crossover indices
        ArrayList<Integer> crossoverIndices = createCrossoverIndices(genomeSize, numCrossovers);

        double[] childGenomeArray = new double[genomeSize];

        // Recombine genomes
        int currentParent = 0;
        for (int gt = 0; gt < genomeSize; gt++) {
            // Change parent if at a crossover point
            if (crossoverIndices.contains(gt)) currentParent = incrementParent(currentParent, numParents);
            childGenomeArray[gt] = parents.getIndividual(currentParent).getGenome().get(genomeArrayIndex)[gt];
        }

        return childGenomeArray;

    }

    /*
        Get crossover indices by sampling without replacement from list of all possible indices
     */
    private ArrayList<Integer> createCrossoverIndices(int genomeSize, int numCrossovers) {

        assert genomeSize > numCrossovers;

        ArrayList<Integer> possibleCrossoverIndices = new ArrayList<>();
        for (int i = 1; i < genomeSize - 1; i++) {
            possibleCrossoverIndices.add(i);
        }
        Collections.shuffle(possibleCrossoverIndices, rand);

        return new ArrayList<Integer>(possibleCrossoverIndices.subList(0, numCrossovers - 1));

    }

}
