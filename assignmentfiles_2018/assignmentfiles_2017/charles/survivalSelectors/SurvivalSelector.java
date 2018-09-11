package charles.survivalSelectors;

import charles.Individual;

import java.util.ArrayList;

public interface SurvivalSelector {
    public ArrayList<Individual> selectSurvivors(ArrayList<Individual> potentialSurvivors, int noSurvivors);
}
