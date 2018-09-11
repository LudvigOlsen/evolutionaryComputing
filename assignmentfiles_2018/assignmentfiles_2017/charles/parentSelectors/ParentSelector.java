package charles.parentSelectors;

import charles.Individual;
import charles.Population;

import java.util.ArrayList;

public interface ParentSelector {
    public Population selectParents(Population population, int noParents);
}
