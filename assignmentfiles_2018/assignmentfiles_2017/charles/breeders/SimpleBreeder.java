package charles.breeders;

import charles.Individual;
import charles.Population;
import charles.mutators.Mutator;
import charles.parentSelectors.ParentSelector;
import charles.recombinators.Recombinator;

public class SimpleBreeder implements Breeder {

    private ParentSelector parentSelector;
    private Recombinator recombinator;
    private Mutator mutator;
    private double minLimit;
    private double maxLimit;


    public SimpleBreeder(ParentSelector parentSelector, Recombinator recombinator, Mutator mutator,
                         double minLimit, double maxLimit) {
        this.parentSelector = parentSelector;
        this.recombinator = recombinator;
        this.mutator = mutator;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    @Override
    public Population breedChildren(Population population, int numParents, int numChildren, int numCrossover) {

        Population children = new Population();

        for (int ch = 0; ch < numChildren; ch++) {
            // Select parents
            Population parents = parentSelector.selectParents(population, numParents);

            // Create Children
            // Recombination
            Individual child = recombinator.combine(parents, numCrossover, minLimit, maxLimit);
            // Mutation

            mutator.mutate(child);

            // Add child to population
            children.addIndividual(child);
        }

        return children;

    }
}
