package charles.recombinators;

import charles.Individual;

import java.util.ArrayList;

public interface Recombinator {
    public Individual combine(ArrayList<Individual> parents, double minLimit, double maxLimit);
}