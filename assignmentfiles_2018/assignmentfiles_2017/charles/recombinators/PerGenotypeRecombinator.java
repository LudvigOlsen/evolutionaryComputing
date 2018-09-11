package charles.recombinators;

import charles.Individual;

import java.util.ArrayList;

public class PerGenotypeRecombinator implements Recombinator {

    /*
    This recombiner merges n parents by randomly selecting genotypes
    from each of the n parents.

    E.g. select from individuals | 0, 1, 3, 2, 3, 0 |

     */
    @Override
    public Individual combine(ArrayList<Individual> parents, double minLimit, double maxLimit) {
        int genomeSize = parents.get(0).getGenomeSize();
        int numIndividuals = parents.size();

        assert genomeSize >= numIndividuals; // Correct way to assert?

        double[] childGenome = new double[genomeSize];

        for (int gt = 0; gt < genomeSize; gt++) {
            int fromIndividual = (int) Math.round(Math.random() * numIndividuals);
            childGenome[gt] = parents.get(fromIndividual).getGenome()[gt];
        }

        return new Individual(childGenome, minLimit, maxLimit);
    }

}
