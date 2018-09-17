package charles.breeders;

import charles.Population;

/**
 * Breeds new individuals
 * Wrapper for calling ParentSelector, Recombinator and Mutator, ending up with a Population of Children
 * that can then be merged with the survivors
 */
public interface Breeder {

    /**
     * Method for breeding children
     * Often looking something like:
     * <p>
     * Select Parents
     * Breed children
     * - Recombination or Cloning
     * Mutate children
     *
     * @return Population
     */
    public Population breedChildren(Population population, int numParents, int numChildren, int numCrossover);


}
