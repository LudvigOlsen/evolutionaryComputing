package charles.mutators;

import charles.Individual;

public interface Mutator {
    /*
    Mutate should mutate the genome of the given individual and replace it
    in-place. I.e. by using individual.setGenome().
     */
    public void mutate(Individual individual);
}
