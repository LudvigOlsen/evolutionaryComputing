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
import charles.survivalSelectors.BestKYoungSurvivalSelector;
import charles.survivalSelectors.SurvivalSelector;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

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


    public player56() {
        rnd_ = new Random();
        System.out.println("Testing");
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

        evaluator = new Evaluator(evaluation_);

    }

    public void run() {
        // Run your algorithm here

        // Select modules here
        ParentSelector parentSelector = new ProportionalParentSelector(rnd_);
        Recombinator recombinator = new PerGenotypeRecombinator();
        Mutator mutator = new NoiseMutator(rnd_, -0.1, 0.1);
        Initializer initializer = new Initializer();
        Breeder breeder = new SimpleBreeder(parentSelector, recombinator, mutator, minLimit, maxLimit);
        SurvivalSelector survivalSelector = new BestKYoungSurvivalSelector();

        int evals = 0;
        // init population

        Population fullPopulation = initializer.initialize(populationSize, genomeSize, minLimit, maxLimit, rnd_);

        // calculate fitness
        evaluator.evaluate(fullPopulation);

        while (evals < evaluations_limit_) {
            Population children = breeder.breedChildren(fullPopulation, numParents, numChildren);

            // Select which from the previous should live on
            Population survivors = survivalSelector.selectSurvivors(fullPopulation, numSurvivors, maxAge);

            survivors.merge(children);
            fullPopulation = survivors; // Overwrite/redefine fullPopulation
            // Check fitness of unknown function
            evaluator.evaluate(fullPopulation);
            System.out.println("Best Fitness " + evaluator.getMaxScore());
            evals++;
        }

    }
}
