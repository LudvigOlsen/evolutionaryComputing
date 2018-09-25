import charles.Evaluator;
import charles.Initializer;
import charles.Population;
import charles.breeders.Breeder;
import charles.breeders.SimpleBreeder;
import charles.mutators.Mutator;
import charles.mutators.UncorrelatedSelfAdaptiveMutator;
import charles.parentSelectors.ParentSelector;
import charles.parentSelectors.ProportionalParentSelector;
import charles.recombinators.UniformRecombinator;
import charles.recombinators.Recombinator;
import charles.survivalSelectors.BestKYoungSurvivalSelector;
import charles.survivalSelectors.SurvivalSelector;
import charles.utils.Numbers;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;

import java.util.*;

public class player56 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    Evaluator evaluator;
    private int evaluations_limit_;

    private int populationSize = 100;
    private List<Integer> genomeArraySizes = Arrays.asList(10, 10);
    private List<Double> minLimits = Arrays.asList(-5.0, 1E-3);
    private List<Double> maxLimits = Arrays.asList(5.0, 1.5);
    private int numCrossover = 5; // Not used with UniformRecombinator
    private int numParents = 2;
    private int numChildren = 90;
    private int numSurvivors = populationSize - numChildren;
    private int maxAge = 2;
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
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));


        // Properties per evaluation function
        // Katsuura is only multimodal
        // BentCigar is neither multimodal, regular or separable
        // Schaffers is both multimodal and regular
        // Sphere is both regular and separable

        // Do sth with property values, e.g. specify relevant settings of your algorithm
        if (isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }

    }

    public void run() {
        // Run your algorithm here

        // Select modules here
        evaluator = new Evaluator(evaluation_, evaluations_limit_);
        ParentSelector parentSelector = new ProportionalParentSelector(rnd_, 5000.0);
        Recombinator recombinator = new UniformRecombinator(rnd_);
        //Mutator mutator = new NoiseMutator(rnd_, Arrays.asList(-0.05, -0.05), Arrays.asList(0.05, 0.05));
        Mutator mutator = new UncorrelatedSelfAdaptiveMutator(rnd_, 1.0,
                1.0);
        Initializer initializer = new Initializer();
        Breeder breeder = new SimpleBreeder(parentSelector, recombinator, mutator, minLimits, maxLimits);
        SurvivalSelector survivalSelector = new BestKYoungSurvivalSelector();

        int numEvaluations = 0;
        // init population

        Population fullPopulation = initializer.initialize(populationSize, genomeArraySizes, minLimits, maxLimits, rnd_);
        if (printProgress) fullPopulation.getIndividual(0).printRepresentation();

        // calculate fitness
        evaluator.evaluate(fullPopulation);

        while (numEvaluations < evaluations_limit_) {
            //System.out.println("eval" + evals);
            Population children = breeder.breedChildren(fullPopulation, numParents, numChildren, numCrossover);

            // Merge with parents
            fullPopulation.merge(children);

            // Check fitness of unknown function
            evaluator.evaluate(fullPopulation);

            // Select which from the previous should live on
            fullPopulation = survivalSelector.selectSurvivors(fullPopulation, populationSize, maxAge);


            // Print max score every n iterations
            if (numEvaluations % showMaxScoreEvery == 0 && printProgress) {
                System.out.print("Iteration: ");
                System.out.print(numEvaluations);
                System.out.print(" - Max score: ");
                System.out.print(Numbers.roundScienceNotationToNDecimals(evaluator.getMaxScore(), 3));
                System.out.print(" - Average score: ");
                System.out.print(Numbers.roundScienceNotationToNDecimals(fullPopulation.getAverageFitnessScore(), 3));
                System.out.print(" - Best Genome: ");
                evaluator.getBestIndividual().printRepresentation();
                System.out.print(" - Best Step Sizes: ");
                evaluator.getBestIndividual().printGenomeArray(1);
                System.out.print(" - All time max score: ");
                System.out.print(Numbers.roundScienceNotationToNDecimals(evaluator.getAlltimeMaxScore(), 3));
                System.out.print(" - All time Best Genome: ");
                evaluator.getAllTimeBestIndividual().printRepresentation();


//                System.out.print(" - Genome 3: "); // For checking that if changes over time
//                fullPopulation.getIndividual(3).printRepresentation();
//                System.out.print(" - Age: ");
//                System.out.print(fullPopulation.getIndividual(3).getAge());
//                System.out.print(" - ID: ");
//                System.out.print(fullPopulation.getIndividual(3).getId());
//                System.out.print(" - Fitness: ");
//                System.out.print(fullPopulation.getIndividual(3).getFitnessScore());
//                System.out.println();
            }

            numEvaluations = evaluator.getTotalNumEvaluations();
        }

        if (printProgress) {
            System.out.print("\nWinning Genome: ");
            evaluator.getAllTimeBestIndividual().printRepresentation();
            System.out.println();
        }


    }
}
