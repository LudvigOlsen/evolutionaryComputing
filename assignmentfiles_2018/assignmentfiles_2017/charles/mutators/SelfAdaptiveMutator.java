package charles.mutators;

import charles.Individual;


import java.util.Random;

public class SelfAdaptiveMutator implements Mutator{
    private Random rand;

    public SelfAdaptiveMutator(Random rand){
        this.rand = rand;
    }

    @Override
    public void mutate(Individual individual){

    }
}
