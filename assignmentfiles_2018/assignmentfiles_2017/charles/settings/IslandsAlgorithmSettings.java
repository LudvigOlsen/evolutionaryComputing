package charles.settings;

import charles.Initializer;
import charles.mutators.Mutator;
import charles.parentSelectors.ParentSelector;
import charles.recombinators.Recombinator;
import charles.survivalSelectors.SurvivalSelector;

import java.util.List;

public class IslandsAlgorithmSettings implements Settings {

    private List<Integer> numPopulations;
    private List<Integer> populationSizes;
    private List<Integer> genomeArraySizes;
    private List<Double> minLimits;
    private List<Double> maxLimits;
    private int numCrossover;
    private int numParents;
    private int numChildren;
    private int maxAge;
    private ParentSelector parentSelector;
    private SurvivalSelector survivalSelector;
    private Recombinator recombinator;
    private Mutator mutator;
    private Initializer initializer;
}
