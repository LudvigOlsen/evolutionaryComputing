package charles.mutators;

import charles.Individual;

public interface Mutator {
    public Individual mutate(Individual individual);
}
