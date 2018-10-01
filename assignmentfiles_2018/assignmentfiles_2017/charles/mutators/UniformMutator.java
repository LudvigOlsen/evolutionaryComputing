package charles.mutators;

import charles.Individual;
import charles.utils.ScaleToRange;

import java.util.Arrays;
import java.util.Random;

public class UniformMutator implements Mutator {
    private Random rand;
    private double upperBound, lowerBound;

    public UniformMutator(Random rand){
        this.rand = rand;
        this.upperBound = 5.0;
        this.lowerBound = -5.0;
    }

    @Override
    public void mutate(Individual individual){
        double newallele = ScaleToRange.scaleToRange(
                rand.nextDouble(),0.0, 1.0, lowerBound, upperBound);

        int allele_place = rand.nextInt((9 - 0) + 1) + 0;
        double[] currentGenotype = individual.getGenome();
        //System.out.println("old: " + Arrays.toString(currentGenotype));
        currentGenotype[allele_place] = newallele;
        //System.out.println("new: " + Arrays.toString(currentGenotype));
    }
}
