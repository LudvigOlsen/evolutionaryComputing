package charles.parentSelectors;

import charles.Population;

public interface ParentSelector {
    public Population selectParents(Population population, int numParents);
}
