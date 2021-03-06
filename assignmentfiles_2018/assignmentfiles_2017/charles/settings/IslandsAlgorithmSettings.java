package charles.settings;

import charles.initializers.Initializer;
import charles.migrators.Migrator;
import charles.mutators.Mutator;
import charles.parentSelectors.ParentSelector;
import charles.recombinators.Recombinator;
import charles.survivalSelectors.SurvivalSelector;

import java.util.List;

public class IslandsAlgorithmSettings implements Settings {


    private List<Integer> populationSizes;
    private List<Integer> genomeArraySizes;
    private List<Double> minLimits;
    private List<Double> maxLimits;
    private List<Integer> chaosFunctions;
    private int numPopulations;
    private int initialEpochSize;
    private int numCrossover;
    private int numParents;
    private int numChildren;
    private int maxAge;
    private int calculateDiversityEvery;
    private int numMigrants;
    private double migrationChangeMultiplier;
    private double epochSizeChangeMultiplier;
    private Boolean usesGlobalization;
    private ParentSelector parentSelector;
    private SurvivalSelector survivalSelector;
    private Recombinator recombinator;
    private Mutator mutator;
    private Initializer initializer;
    private Migrator migrator;


    public IslandsAlgorithmSettings(List<Integer> populationSizes, List<Integer> genomeArraySizes,
                                    List<Double> minLimits, List<Double> maxLimits, List<Integer> chaosFunctions,
                                    int numPopulations, int initialEpochSize,
                                    int numCrossover, int numParents,
                                    int numChildren, int maxAge, int calculateDiversityEvery, int numMigrants,
                                    double migrationChangeMultiplier, double epochSizeChangeMultiplier,
                                    Boolean usesGlobalization, ParentSelector parentSelector,
                                    SurvivalSelector survivalSelector, Recombinator recombinator,
                                    Mutator mutator, Initializer initializer, Migrator migrator) {
        this.populationSizes = populationSizes;
        this.genomeArraySizes = genomeArraySizes;
        this.minLimits = minLimits;
        this.maxLimits = maxLimits;
        this.chaosFunctions = chaosFunctions;
        this.numPopulations = numPopulations;
        this.initialEpochSize = initialEpochSize;
        this.numCrossover = numCrossover;
        this.numParents = numParents;
        this.numChildren = numChildren;
        this.maxAge = maxAge;
        this.calculateDiversityEvery = calculateDiversityEvery;
        this.numMigrants = numMigrants;
        this.migrationChangeMultiplier = migrationChangeMultiplier;
        this.epochSizeChangeMultiplier = epochSizeChangeMultiplier;
        this.usesGlobalization = usesGlobalization;
        this.parentSelector = parentSelector;
        this.survivalSelector = survivalSelector;
        this.recombinator = recombinator;
        this.mutator = mutator;
        this.initializer = initializer;
        this.migrator = migrator;
    }

    public int getNumMigrants() {
        return numMigrants;
    }

    public void setNumMigrants(int numMigrants) {
        this.numMigrants = numMigrants;
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

    public int getNumPopulations() {
        return numPopulations;
    }

    public void setNumPopulations(int numPopulations) {
        this.numPopulations = numPopulations;
    }

    public List<Integer> getPopulationSizes() {
        return populationSizes;
    }

    public void setPopulationSizes(List<Integer> populationSizes) {
        this.populationSizes = populationSizes;
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

    public int getInitialEpochSize() {
        return initialEpochSize;
    }

    public void setInitialEpochSize(int initialEpochSize) {
        this.initialEpochSize = initialEpochSize;
    }

    public Boolean getUsesGlobalization() {
        return usesGlobalization;
    }

    public void setUsesGlobalization(Boolean usesGlobalization) {
        this.usesGlobalization = usesGlobalization;
    }

    public int getCalculateDiversityEvery() {
        return calculateDiversityEvery;
    }

    public void setCalculateDiversityEvery(int calculateDiversityEvery) {
        this.calculateDiversityEvery = calculateDiversityEvery;
    }

    public Migrator getMigrator() {
        return migrator;
    }

    public void setMigrator(Migrator migrator) {
        this.migrator = migrator;
    }

    public double getMigrationChangeMultiplier() {
        return migrationChangeMultiplier;
    }

    public void setMigrationChangeMultiplier(double migrationChangeMultiplier) {
        this.migrationChangeMultiplier = migrationChangeMultiplier;
    }

    public double getEpochSizeChangeMultiplier() {
        return epochSizeChangeMultiplier;
    }

    public void setEpochSizeChangeMultiplier(double epochSizeChangeMultiplier) {
        this.epochSizeChangeMultiplier = epochSizeChangeMultiplier;
    }

    public List<Integer> getChaosFunctions() {
        return chaosFunctions;
    }

    public void setChaosFunctions(List<Integer> chaosFunctions) {
        this.chaosFunctions = chaosFunctions;
    }
}
