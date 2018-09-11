package charles.recombinators;

import charles.Individual;
import charles.Population;

public interface Recombinator {
    public Individual combine(Population parents, double minLimit, double maxLimit);
}