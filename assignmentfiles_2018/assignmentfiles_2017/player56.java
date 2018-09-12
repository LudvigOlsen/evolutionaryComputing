import charles.Evaluator;
import charles.Initializer;
import charles.Population;
import charles.breeders.Breeder;
import charles.breeders.SimpleBreeder;
import charles.mutators.Mutator;
import charles.mutators.NoiseMutator;
import charles.parentSelectors.ParentSelector;
import charles.parentSelectors.ProportionalParentSelector;
import charles.recombinators.PerGenotypeRecombinator;
import charles.recombinators.Recombinator;
import charles.survivalSelectors.BestKSurvivalSelector;
import charles.survivalSelectors.SurvivalSelector;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;

import java.util.Properties;
import java.util.Random;

public class player56 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    Evaluator evaluator;
    private int evaluations_limit_;

    private int populationSize = 100;
    private int genomeSize = 10;
    private double minLimit = -5.0;
    private double maxLimit = 5.0;
    private int numParents = 2;
    private int numChildren = 80;
    private int numSurvivors = populationSize - numChildren;
    private int maxAge = 2;
    private int showMaxScoreEvery = 500;


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
        System.out.println(evaluations_limit_);
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

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
        evaluator = new Evaluator(evaluation_);
        ParentSelector parentSelector = new ProportionalParentSelector(rnd_);
        Recombinator recombinator = new PerGenotypeRecombinator(rnd_);
        Mutator mutator = new NoiseMutator(rnd_, -0.05, 0.05);
        Initializer initializer = new Initializer();
        Breeder breeder = new SimpleBreeder(parentSelector, recombinator, mutator, minLimit, maxLimit);
        SurvivalSelector survivalSelector = new BestKSurvivalSelector();

        int evals = 0;
        // init population

        Population fullPopulation = initializer.initialize(populationSize, genomeSize, minLimit, maxLimit, rnd_);
        fullPopulation.getIndividual(0).printGenome();


        // calculate fitness
        evaluator.evaluate(fullPopulation);

        while (evals < evaluations_limit_) {
            //System.out.println("eval" + evals);
            Population children = breeder.breedChildren(fullPopulation, numParents, numChildren);

            // Select which from the previous should live on
            Population survivors = survivalSelector.selectSurvivors(fullPopulation, numSurvivors, maxAge);

            survivors.merge(children);
            fullPopulation = survivors; // Overwrite/redefine fullPopulation

            // Check fitness of unknown function
            evaluator.evaluate(fullPopulation);

            // Print max score every n iterations
            if (evals % showMaxScoreEvery == 0) {
                System.out.print("Iteration: ");
                System.out.print(evals);
                System.out.print(" - Max score: ");
                System.out.print(evaluator.getMaxScore());
                System.out.print(" - Average score: ");
                System.out.println(fullPopulation.getAverageFitnessScore());
            }

            evals++;
        }

    }
}
