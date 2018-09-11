package charles.recombinators;

import charles.Individual;
import charles.Population;

public class PerGenotypeRecombinator implements Recombinator {

    /*
    This recombiner merges n parents by randomly selecting genotypes
    from each of the n parents.

    E.g. select from individuals | 0, 1, 3, 2, 3, 0 |

     */
    @Override
    public Individual combine(Population parents, double minLimit, double maxLimit) {
        int genomeSize = parents.getIndividual(0).getGenomeSize();
        int numIndividuals = parents.getPopulationSize();

        assert genomeSize >= numIndividuals; // Correct way to assert?

        double[] childGenome = new double[genomeSize];

        for (int gt = 0; gt < genomeSize; gt++) {
            int fromIndividual = (int) Math.round(Math.random() * numIndividuals); // TODO should use Random() instead
            childGenome[gt] = parents.getIndividual(fromIndividual).getGenome()[gt];
        }

        return new Individual(childGenome, minLimit, maxLimit);
    }

}
