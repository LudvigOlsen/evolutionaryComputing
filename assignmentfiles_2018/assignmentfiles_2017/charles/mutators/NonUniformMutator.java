package charles.mutators;

import charles.Individual;

import java.util.Random;

public class NonUniformMutator implements Mutator {
    private Random rand;
    private double uppderbound, lowerbound;
    private double mutationStepSize;

    public NonUniformMutator(Random rand, double mutStepSize){
        this.rand = rand;
        uppderbound = 5.0;
        lowerbound = -5.0;
        this.mutationStepSize = mutStepSize;
    }

    @Override
    public void mutate(Individual individual){



        double[] newGenome = new double[individual.getGenomeSize()];

        for (int gt = 0; gt < individual.getGenomeSize(); gt++) {
            double currentAllele = individual.getGenome()[gt];
            double newAllele = currentAllele + mutationValueGaussian();

            if (newAllele > uppderbound)
                newAllele = uppderbound;
            if (newAllele < lowerbound)
                newAllele = lowerbound;

            newGenome[gt] = newAllele;
        }

        individual.setGenome(newGenome);
    }

    public double mutationValueGaussian(){
        double mutationValue = rand.nextGaussian()*mutationStepSize;

        if (mutationValue > uppderbound)
            return mutationValue = uppderbound;

        if (mutationValue < lowerbound)
            return mutationValue = lowerbound;

        return mutationValue;
    }
}
