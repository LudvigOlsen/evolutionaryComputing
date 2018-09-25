package charles.survivalSelectors;

import charles.Population;

public interface SurvivalSelector {
    public Population selectSurvivors(Population population, int numSurvivors, int maxAge);
}
