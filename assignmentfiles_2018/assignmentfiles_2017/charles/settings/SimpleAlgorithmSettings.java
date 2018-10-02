package charles.settings;

import charles.Initializer;
import charles.mutators.Mutator;
import charles.parentSelectors.ParentSelector;
import charles.recombinators.Recombinator;
import charles.survivalSelectors.SurvivalSelector;

import java.util.List;

public class SimpleAlgorithmSettings implements Settings {

    private int populationSize;
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

    public SimpleAlgorithmSettings(int populationSize, List<Integer> genomeArraySizes, List<Double> minLimits,
                                   List<Double> maxLimits, int numCrossover, int numParents, int numChildren,
                                   int maxAge, ParentSelector parentSelector, SurvivalSelector survivalSelector,
                                   Recombinator recombinator, Mutator mutator, Initializer initializer) {

        this.populationSize = populationSize;
        this.genomeArraySizes = genomeArraySizes;
        this.minLimits = minLimits;
        this.maxLimits = maxLimits;
        this.numCrossover = numCrossover;
        this.numParents = numParents;
        this.numChildren = numChildren;
        this.maxAge = maxAge;
        this.parentSelector = parentSelector;
        this.survivalSelector = survivalSelector;
        this.recombinator = recombinator;
        this.mutator = mutator;
        this.initializer = initializer;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public List<Integer> getGenomeArraySizes() {
        return genomeArraySizes;
    }

    public void setGenomeArraySizes(List<Integer> genomeArraySizes) {
        this.genomeArraySizes = genomeArraySizes;
    }

    public List<Double> getMinLimits() {
        return minLimits;
    }

    public void setMinLimits(List<Double> minLimits) {
        this.minLimits = minLimits;
    }

    public List<Double> getMaxLimits() {
        return maxLimits;
    }

    public void setMaxLimits(List<Double> maxLimits) {
        this.maxLimits = maxLimits;
    }

    public int getNumCrossover() {
        return numCrossover;
    }

    public void setNumCrossover(int numCrossover) {
        this.numCrossover = numCrossover;
    }

    public int getNumParents() {
        return numParents;
    }

    public void setNumParents(int numParents) {
        this.numParents = numParents;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public ParentSelector getParentSelector() {
        return parentSelector;
    }

    public void setParentSelector(ParentSelector parentSelector) {
        this.parentSelector = parentSelector;
    }

    public SurvivalSelector getSurvivalSelector() {
        return survivalSelector;
    }

    public void setSurvivalSelector(SurvivalSelector survivalSelector) {
        this.survivalSelector = survivalSelector;
    }

    public Recombinator getRecombinator() {
        return recombinator;
    }

    public void setRecombinator(Recombinator recombinator) {
        this.recombinator = recombinator;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }
}
