package charles.survivalSelectors;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;

public interface SurvivalSelector {
    public Population selectSurvivors(Population population, int numSurvivors, int maxAge);
}
