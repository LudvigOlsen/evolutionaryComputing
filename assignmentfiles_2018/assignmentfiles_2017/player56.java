import charles.Evaluator;
import charles.Population;
import charles.breeders.Breeder;
import charles.breeders.IslandBreeder;
import charles.breeders.SimpleBreeder;
import charles.initializers.BasicInitializer;
import charles.initializers.Initializer;
import charles.settings.IslandsAlgorithmSettings;
import charles.settings.Presets;
import charles.settings.SimpleAlgorithmSettings;
import charles.utils.Numbers;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import static charles.World.*;
import static charles.utils.Numbers.roundScienceNotationToNDecimals;
import static charles.utils.Numbers.roundToInt;

public class player56 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    Evaluator evaluator;
    private int evaluations_limit_;

    private boolean isMultimodal, isSeparable, hasStructure;

    private SimpleAlgorithmSettings simpleSettings;
    private IslandsAlgorithmSettings islandsAlgorithmSettings;

    private ArrayList<Population> islands;
    private ArrayList<IslandBreeder> islandBreeder;

    private String modelStructure = "islands"; // "simple or "islands" or divergenceMetric
    private int showMaxScoreEvery = 500;
    private Boolean printProgress = false; // TODO Turn off for submissions!
    private Boolean printDiversity = true; // This is separate from the above. Should still be false for contest submissions.

    public player56() {
        rnd_ = new Random();
    }

    public void setSeed(long seed) {
        // Set seed of algorithms random process
        rnd_.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation) {
        // Set evaluation problem used in the run
        evaluation_ = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
        if (printProgress) System.out.println(evaluations_limit_);
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));


    }

    public void run() {
        // Run your algorithm here

        // Properties per evaluation function
        // Katsuura is only multimodal
        // BentCigar is neither multimodal, regular or separable
        // Schaffers is both multimodal and regular
        // Sphere is both regular and separable

        if (modelStructure.equals("divergenceMetric")) {
            testDivergenceMetric();
            throw new IllegalArgumentException("Test is done. No worries.");
        }

        if (modelStructure.equals("simple")) {
            // Get simpleSettings for the current evaluation function
            if (isMultimodal && !hasStructure) {
                // Katsuura simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using Katsuura Settings");
            } else if (isMultimodal) {
                // Schaffers simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettingsChaosInit(rnd_);
                if (printProgress) System.out.println("Using Schaffers Settings");
            } else if (!hasStructure && !isSeparable) {
                // Bent Cigar simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using BentCigar Settings");
            } else if (hasStructure && isSeparable) {
                // Sphere simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using Sphere Settings");
            }
        } else if (modelStructure.equals("islands")) {
            // Get islandSettings for the current evaluation function
            // TODO Change settings here when islands are implemented
            if (isMultimodal && !hasStructure) {
                // Katsuura simpleSettings
                islandsAlgorithmSettings = Presets.basicIslandSettingsKatsuuraNoMigration(rnd_);
                if (printProgress) System.out.println("Using Katsuura Settings");
            } else if (isMultimodal) {
                // Schaffers simpleSettings
                islandsAlgorithmSettings = Presets.basicIslandSettingsChaosInit5Islands(rnd_);
                if (printProgress) System.out.println("Using Schaffers Settings");
            } else if (!hasStructure && !isSeparable) {
                // Bent Cigar simpleSettings
                islandsAlgorithmSettings = Presets.basicIslandSettings1(rnd_);
                if (printProgress) System.out.println("Using BentCigar Settings");
            } else if (hasStructure && isSeparable) {
                // Sphere simpleSettings
                islandsAlgorithmSettings = Presets.basicIslandSettings1(rnd_);
                if (printProgress) System.out.println("Using Sphere Settings");
            }

        }

        if (modelStructure.equals("simple")) runSimpleStructureModel();
        else if (modelStructure.equals("islands")) runIslandsStructureModel();

        if (printProgress) {
            System.out.print("\nWinning Genome: ");
            evaluator.getAllTimeBestIndividual().printRepresentation();
            System.out.println();
        }

    }

    private void testDivergenceMetric() {
        Initializer initializer = new BasicInitializer(rnd_);
        Population populationOne = initializer.initialize(100,
                Arrays.asList(10, 1), Arrays.asList(-5.0, 0.0), // The extras are not used
                Arrays.asList(5.0, 1.0));

        Population populationTwo = initializer.initialize(100,
                Arrays.asList(10, 1), Arrays.asList(-5.0, 0.0), // The extras are not used
                Arrays.asList(5.0, 1.0));

        populationOne.calculateGenomeProduct(10);
        populationTwo.calculateGenomeProduct(10);

        double divergenceOneTwo = populationOne.absProductKullbackLeiblerDivergence(populationTwo);
        double divergenceTwoOne = populationTwo.absProductKullbackLeiblerDivergence(populationOne);
        double divergenceOneOne = populationOne.absProductKullbackLeiblerDivergence(populationOne);
        double divergenceTwoTwo = populationTwo.absProductKullbackLeiblerDivergence(populationTwo);

        if (printProgress) {
            System.out.print("Divergences (OneTwo, TwoOne, OneOne, TwoTwo): ");
            System.out.print(divergenceOneTwo);
            System.out.print("  ");
            System.out.print(divergenceTwoOne);
            System.out.print("  ");
            System.out.print(divergenceOneOne);
            System.out.print("  ");
            System.out.println(divergenceTwoTwo);
            System.out.println();
        }

    }

    private void runSimpleStructureModel() {
        // Select modules here
        evaluator = new Evaluator(evaluation_, evaluations_limit_);
        int numEvaluations = 0;

        Initializer initializer = simpleSettings.getInitializer();
        Breeder breeder = new SimpleBreeder(simpleSettings.getParentSelector(),
                simpleSettings.getRecombinator(), simpleSettings.getMutator(),
                simpleSettings.getMinLimits(), simpleSettings.getMaxLimits());

        // init population
        Population fullPopulation = initializer.initialize(simpleSettings.getPopulationSize(),
                simpleSettings.getGenomeArraySizes(), simpleSettings.getMinLimits(),
                simpleSettings.getMaxLimits());

        if (printProgress) fullPopulation.getIndividual(0).printRepresentation();

        // calculate fitness
        evaluator.evaluate(fullPopulation);

        while (numEvaluations < evaluations_limit_) {
            //System.out.println("eval" + evals);
            Population children = breeder.breedChildren(fullPopulation, simpleSettings.getNumParents(),
                    simpleSettings.getNumChildren(), simpleSettings.getNumCrossover());

            // Merge with parents
            fullPopulation.merge(children);

            // Check fitness on unknown function
            evaluator.evaluate(fullPopulation);

            // Select which from the previous should live on
            fullPopulation = simpleSettings.getSurvivalSelector().selectSurvivors(fullPopulation,
                    simpleSettings.getPopulationSize(), simpleSettings.getMaxAge());

            // Print progress
            progressPrinter(numEvaluations, showMaxScoreEvery, printProgress,
                    evaluator, fullPopulation.getAverageFitnessScore());

            numEvaluations = evaluator.getTotalNumEvaluations();
        }

    }

    private void runIslandsStructureModel() {

        // Select modules here
        evaluator = new Evaluator(evaluation_, evaluations_limit_);
        islands = new ArrayList<>();
        islandBreeder = new ArrayList<>();
        int numEvaluations = 0;
        int numGenerations = 0;

        // We will have the number of migrants as a double, so we 
        // can keep multiplying it with the migration rate
        // We then use the rounded version for the current migration
        double numMigrationsContinuous = (double) islandsAlgorithmSettings.getNumMigrants();
        int numMigrationsRounded = roundToInt(numMigrationsContinuous);

        double epochSizeContinuous = (double) islandsAlgorithmSettings.getInitialEpochSize();
        int epochSizeRounded = roundToInt(epochSizeContinuous);

        double interPopulationDiversity;
        double globalDiversity;
        double averageGlobalFitness;
        ArrayList<Double> islandsAverageFitnesses = new ArrayList<>();

        // TODO Create initializer that checks and uses the initializer setting for each island
        // TODO Create islands breeder

        Initializer initializer = islandsAlgorithmSettings.getInitializer();

        // Initialize the islands and run initial evaluation
        for (int i = 0; i < islandsAlgorithmSettings.getNumPopulations(); i++) {

            initializer.setChaosFn(islandsAlgorithmSettings.getChaosFunctions().get(i));
            islands.add(initializer.initialize(islandsAlgorithmSettings.getPopulationSizes().get(i),
                    islandsAlgorithmSettings.getGenomeArraySizes(), islandsAlgorithmSettings.getMinLimits(),
                    islandsAlgorithmSettings.getMaxLimits()));

            // Create a breeder for each island. This way they can vary if we want them to.
            islandBreeder.add(new IslandBreeder(islandsAlgorithmSettings.getParentSelector(),
                    islandsAlgorithmSettings.getRecombinator(), islandsAlgorithmSettings.getMutator(),
                    islandsAlgorithmSettings.getMinLimits(), islandsAlgorithmSettings.getMaxLimits()));

            // TODO Maybe initialize the islands n times and choose the one with largest divergence?

            // Calculate fitness
            evaluator.evaluate(islands.get(i));

        }

        numGenerations++;

        if (printProgress) System.out.println("\nStarting evolution\n");

        while (numEvaluations < evaluations_limit_) {

            // Recombine, mutate, survivor selection for each island
            for (int i = 0; i < islandsAlgorithmSettings.getNumPopulations(); i++) {

                if (numEvaluations >= evaluations_limit_) break;

                Population children = islandBreeder.get(i).breedChildren(islands.get(i),
                        islandsAlgorithmSettings.getNumParents(), islandsAlgorithmSettings.getNumChildren(),
                        islandsAlgorithmSettings.getNumCrossover());

                // Merge with parents
                islands.get(i).merge(children);

                // Check fitness on unknown function
                evaluator.evaluate(islands.get(i));

                // Select survivors
                islands.set(i, islandsAlgorithmSettings.getSurvivalSelector()
                        .selectSurvivors(islands.get(i), islandsAlgorithmSettings.getPopulationSizes().get(i),
                                islandsAlgorithmSettings.getMaxAge()));

                numEvaluations = evaluator.getTotalNumEvaluations();
            }

            numGenerations++;

            // Update migration rate
            numMigrationsContinuous *= islandsAlgorithmSettings.getMigrationChangeMultiplier();
            numMigrationsRounded = roundToInt(numMigrationsContinuous);

            epochSizeContinuous *= islandsAlgorithmSettings.getEpochSizeChangeMultiplier();
            epochSizeRounded = Math.max(1, roundToInt(epochSizeContinuous)); // At least 1

            if ((numGenerations == 0 || numGenerations % islandsAlgorithmSettings.getCalculateDiversityEvery() == 0) && printDiversity) {

                // Calculate diversities
                interPopulationDiversity = calculateInterPopulationDiversity(islands);
                globalDiversity = calculateGlobalStdDiversity(islands);

                // Calculate fitnesses
                averageGlobalFitness = calculateAverageGlobalFitness(islands);

                islandsAverageFitnesses.clear();
                for (Population island : islands) {
                    islandsAverageFitnesses.add(island.getAverageFitnessScore());
                }

                // Print progress
                System.out.print("Generation: ");
                System.out.print(numGenerations);
                System.out.print(" , Inter-Island Diversity: ");
                System.out.print(roundScienceNotationToNDecimals(interPopulationDiversity, 5));
                System.out.print(" , Global Diversity: ");
                System.out.print(roundScienceNotationToNDecimals(globalDiversity, 5));
                System.out.print(" , # Migrants: ");
                System.out.print(numMigrationsRounded);
                System.out.print(" , Epoch size: ");
                System.out.print(epochSizeRounded);
                System.out.print(" , Best score so far: ");
                System.out.print(roundScienceNotationToNDecimals(evaluator.getAlltimeMaxScore(), 5));
                System.out.print(" , Avg Global Fitness: ");
                System.out.print(roundScienceNotationToNDecimals(averageGlobalFitness, 5));
                for (int f = 0; f < islandsAverageFitnesses.size(); f++) {
                    System.out.print(" Island ");
                    System.out.print(f);
                    System.out.print(": ");
                    System.out.print(roundScienceNotationToNDecimals(islandsAverageFitnesses.get(f), 5));
                }
                System.out.println();
            }

            if (numGenerations % epochSizeRounded == 0 && islandsAlgorithmSettings.getUsesGlobalization()) {
                if (printProgress) System.out.println("Merging");
                islandsAlgorithmSettings.getMigrator().migrate(islands, numMigrationsRounded);
            }

            // Print progress
            progressPrinter(numEvaluations, showMaxScoreEvery, printProgress,
                    evaluator, getAverageFitness(islands));

//            numEvaluations = evaluator.getTotalNumEvaluations();
        }

    }

    private void progressPrinter(int numEvaluations, int showMaxScoreEvery, Boolean printProgress,
                                 Evaluator evaluator, double averageFitness) {
        // Print max score every n iterations
        if (numEvaluations % showMaxScoreEvery == 0 && printProgress) {
            System.out.print("Iteration: ");
            System.out.print(numEvaluations);
            System.out.print(" - Max score: ");
            System.out.print(Numbers.roundScienceNotationToNDecimals(evaluator.getMaxScore(), 3));
            System.out.print(" - Average score: ");
            System.out.print(Numbers.roundScienceNotationToNDecimals(averageFitness, 3));
            System.out.print(" - Best Genome: ");
            evaluator.getBestIndividual().printRepresentation();
            System.out.print(" - Best Step Sizes: ");
            evaluator.getBestIndividual().printGenomeArray(1);
            System.out.print(" - All time max score: ");
            System.out.print(Numbers.roundScienceNotationToNDecimals(evaluator.getAlltimeMaxScore(), 3));
            System.out.print(" - All time Best Genome: ");
            evaluator.getAllTimeBestIndividual().printRepresentation();
        }

    }
}
