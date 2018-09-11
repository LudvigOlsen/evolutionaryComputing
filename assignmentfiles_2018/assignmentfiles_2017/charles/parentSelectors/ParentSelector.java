package charles.parentSelectors;

import charles.Individual;

import java.util.ArrayList;

public interface ParentSelector {
    public ArrayList<Individual> selectParents(ArrayList<Individual> potentialParents, int noParents);
}
