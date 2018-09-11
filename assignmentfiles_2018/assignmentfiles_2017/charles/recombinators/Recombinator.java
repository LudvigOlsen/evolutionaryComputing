package charles.recombinators;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;

public interface Recombinator {
    public Individual combine(Population parents, double minLimit, double maxLimit);
}