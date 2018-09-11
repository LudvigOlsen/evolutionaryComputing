package charles;

import java.util.ArrayList;

public class Recombine1 {

    /*
    This recombiner merges n individual by randomly selecting genotypes
    from each of the n individuals.

    E.g. select from individuals | 0, 1, 3, 2, 3 |

     */
    public Individual recombine(ArrayList<Individual> parents) {
        int genomeSize = parents.get(0).getGenomeSize();
        int numIndividuals = parents.size();
        
        assert genomeSize >= numIndividuals; // Correct way to assert?

        double[] childGenome = new double[genomeSize];

        for (int gt = 0; gt < genomeSize; gt++) {
            int fromIndividual = (int) Math.round(Math.random() * numIndividuals);
            childGenome[gt] = parents.get(fromIndividual).getGenome()[gt];
        }

        return new Individual(childGenome);
    }

}
