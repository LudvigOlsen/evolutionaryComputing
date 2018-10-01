package charles.mutators;

import charles.Individual;


import java.util.Random;

/*
For this mutator to do it's job, individuals must have a mutations step size in them so that that can evolve
with the individual.
 */
public class SelfAdaptiveMutator implements Mutator{
    private Random rand;
    private double learningRate;
    private double uppderbound, lowerbound;

    public SelfAdaptiveMutator(Random rand, double uppderbound, double lowerbound){
        this.rand = rand;
        this.learningRate = 1;
    }

    @Override
    public void mutate(Individual individual){
        // Mutate the mutations strategy first
        mutateMutationStrategy(individual);


        double mutStepSize = individual.getMutStrategy();


        // Mutate the gene now
        double[] newGenome = new double[individual.getGenomeSize()];

        for (int gt = 0; gt < individual.getGenomeSize(); gt++) {

            double currentAllele = individual.getGenome()[gt];

            double newAllele = currentAllele + rand.nextGaussian() * mutStepSize;

            if (newAllele > uppderbound)
                newAllele = uppderbound;
            if (newAllele < lowerbound)
                newAllele = lowerbound;

            newGenome[gt] = newAllele;
        }

        individual.setGenome(newGenome);
    }

    public void mutateMutationStrategy(Individual individual){
        double oldMutStep = individual.getMutStrategy();
        System.out.println("Old :" + oldMutStep);
        double newMutStep = oldMutStep * java.lang.Math.exp(learningRate * rand.nextGaussian());
        System.out.println("new: " + newMutStep);

        if (newMutStep < 0.01){
            //System.out.println("Very Small: " + newMutStep);
            newMutStep = 0.01;
        }
        individual.setMutStrategy(newMutStep);
    }
}
