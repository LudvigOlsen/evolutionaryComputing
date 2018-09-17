package charles.recombinators;

import charles.Individual;
import charles.Population;

public interface Recombinator {
    public Individual combine(Population parents, int numCrossovers, double minLimit, double maxLimit);
}