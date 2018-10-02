import charles.Evaluator;
import charles.Initializer;
import charles.Population;
import charles.breeders.Breeder;
import charles.breeders.SimpleBreeder;
import charles.settings.IslandsAlgorithmSettings;
import charles.settings.Presets;
import charles.settings.SimpleAlgorithmSettings;
import charles.utils.Numbers;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;

import java.util.Properties;
import java.util.Random;

public class player56 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    Evaluator evaluator;
    private int evaluations_limit_;

    private boolean isMultimodal, hasStructure, isSeparable, isRegular;

    private SimpleAlgorithmSettings simpleSettings;
    private IslandsAlgorithmSettings islandsAlgorithmSettings;

    private String modelStructure = "simple"; // Or "islands"
    private int showMaxScoreEvery = 500;
    private Boolean printProgress = true; // TODO Turn off for submissions!

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
        isRegular = Boolean.parseBoolean(props.getProperty("Regular"));


    }

    public void run() {
        // Run your algorithm here

        // Properties per evaluation function
        // Katsuura is only multimodal
        // BentCigar is neither multimodal, regular or separable
        // Schaffers is both multimodal and regular
        // Sphere is both regular and separable

        if (modelStructure.equals("simple")) {
            // Get simpleSettings for the current evaluation function
            if (isMultimodal && !isRegular) {
                // Katsuura simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using Katsuura Settings");
            } else if (isMultimodal) {
                // Schaffers simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettings2(rnd_);
                if (printProgress) System.out.println("Using Schaffers Settings");
            } else if (!isRegular && !isSeparable) {
                // Bent Cigar simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using BentCigar Settings");
            } else if (isRegular && isSeparable) {
                // Sphere simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using Sphere Settings");
            }
        } else if (modelStructure.equals("islands")) {
            // Get islandSettings for the current evaluation function
            // TODO Change settings here when Islands are implemented
            if (isMultimodal && !isRegular) {
                // Katsuura simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using Katsuura Settings");
            } else if (isMultimodal) {
                // Schaffers simpleSettings
                simpleSettings = Presets.NStepUncorrelatedMutationSettings2(rnd_);
                if (printProgress) System.out.println("Using Schaffers Settings");
            } else if (!isRegular && !isSeparable) {
                // Bent Cigar simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
                if (printProgress) System.out.println("Using BentCigar Settings");
            } else if (isRegular && isSeparable) {
                // Sphere simpleSettings
                simpleSettings = Presets.OneStepUncorrelatedMutationSettings1(rnd_);
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
                simpleSettings.getMaxLimits(), rnd_);

        if (printProgress) fullPopulation.getIndividual(0).printRepresentation();

        // calculate fitness
        evaluator.evaluate(fullPopulation);

        while (numEvaluations < evaluations_limit_) {
            //System.out.println("eval" + evals);
            Population children = breeder.breedChildren(fullPopulation, simpleSettings.getNumParents(),
                    simpleSettings.getNumChildren(), simpleSettings.getNumCrossover());

            // Merge with parents
            fullPopulation.merge(children);

            // Check fitness of unknown function
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
        int numEvaluations = 0;

        // TODO Create initializer that checks and uses the initializer setting for each tribe
        // TODO Create islands breeder

        while (numEvaluations < evaluations_limit_) {

            // TODO Fill in loop
            
            // Print progress
//            progressPrinter(numEvaluations, showMaxScoreEvery, printProgress,
//                    evaluator, fullPopulation.getAverageFitnessScore());

            numEvaluations = evaluator.getTotalNumEvaluations();
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
