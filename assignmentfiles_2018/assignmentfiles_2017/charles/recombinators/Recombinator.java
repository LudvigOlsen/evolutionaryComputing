package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.List;

public interface Recombinator {
    public Individual combine(Population parents, int numCrossovers,
                              List<Double> minLimits, List<Double> maxLimits);
}