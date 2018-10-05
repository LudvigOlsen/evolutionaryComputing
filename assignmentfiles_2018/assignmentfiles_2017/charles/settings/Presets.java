package charles.settings;

import charles.Initializer;
import charles.mutators.CorrelatedSelfAdaptiveMutator;
import charles.mutators.UncorrelatedSelfAdaptiveNStepsMutator;
import charles.mutators.UncorrelatedSelfAdaptiveOneStepMutator;
import charles.parentSelectors.LinearRankingSelector;
import charles.parentSelectors.ProportionalParentSelector;
import charles.recombinators.UniformRecombinator;
import charles.survivalSelectors.BestKYoungSurvivalSelector;

import java.util.Arrays;
import java.util.Random;

import static charles.mutators.CorrelatedSelfAdaptiveMutator.calculateNumAlphas;

public class Presets {

    public static SimpleAlgorithmSettings OneStepUncorrelatedMutationSettings1(Random rand) {

        return new SimpleAlgorithmSettings(
                100,
                Arrays.asList(10, 1),        // Genome Array Sizes
                Arrays.asList(-5.0, 1E-4),   // Minimum Limits 
                Arrays.asList(5.0, 2.5),     // Maximum Limits
                5,
                2,
                90,
                2,
                new LinearRankingSelector(rand, 2),
                new BestKYoungSurvivalSelector(),
                new UniformRecombinator(rand),
                new UncorrelatedSelfAdaptiveOneStepMutator(rand, 1.0),
                new Initializer()
        );

    }

    public static SimpleAlgorithmSettings NStepUncorrelatedMutationSettings1(Random rand) {

        return new SimpleAlgorithmSettings(
                100,
                Arrays.asList(10, 10),       // Genome Array Sizes
                Arrays.asList(-5.0, 1E-4),   // Minimum Limits 
                Arrays.asList(5.0, 2.5),     // Maximum Limits
                5,
                2,
                90,
                2,
                new LinearRankingSelector(rand, 2),
                new BestKYoungSurvivalSelector(),
                new UniformRecombinator(rand),
                new UncorrelatedSelfAdaptiveNStepsMutator(rand,
                        0.22, 0.39),
                new Initializer()
        );

    }

    public static SimpleAlgorithmSettings NStepUncorrelatedMutationSettings2(Random rand) {

        return new SimpleAlgorithmSettings(
                100,
                Arrays.asList(10, 10),       // Genome Array Sizes
                Arrays.asList(-5.0, 1E-4),   // Minimum Limits 
                Arrays.asList(5.0, 2.5),     // Maximum Limits
                5,
                2,
                90,
                2,
                new ProportionalParentSelector(rand, 5000),
                new BestKYoungSurvivalSelector(),
                new UniformRecombinator(rand),
                new UncorrelatedSelfAdaptiveNStepsMutator(rand,
                        0.22, 0.39),
                new Initializer()
        );

    }


    public static SimpleAlgorithmSettings CorrelatedMutationSettings1(Random rand) {

        return new SimpleAlgorithmSettings(
                100,
                Arrays.asList(10, 10, calculateNumAlphas(10)),  // Genome Array Sizes
                Arrays.asList(-5.0, 1E-4, -Math.PI),               // Minimum Limits 
                Arrays.asList(5.0, 2.5, Math.PI),                  // Maximum Limits
                5,
                2,
                90,
                2,
                new LinearRankingSelector(rand, 2),
                new BestKYoungSurvivalSelector(),
                new UniformRecombinator(rand),
                new CorrelatedSelfAdaptiveMutator(rand,
                        1.0, 1.0, 5),
                new Initializer()
        );

    }


}

/*
private int populationSize = 100;
    private List<Integer> genomeArraySizes = Arrays.asList(10, 1); //, calculateNumAlphas(10));
    private List<Double> minLimits = Arrays.asList(-5.0, 1E-4); //, -Math.PI);
    private List<Double> maxLimits = Arrays.asList(5.0, 2.5); //, Math.PI);
    private int numCrossover = 5; // Not used with UniformRecombinator
    private int numParents = 2;
    private int numChildren = 90;
    // private int numSurvivors = populationSize - numChildren;
    private int maxAge = 2;
 */

 