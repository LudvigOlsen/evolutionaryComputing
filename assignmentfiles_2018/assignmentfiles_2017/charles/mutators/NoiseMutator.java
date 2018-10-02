package charles.mutators;

import charles.Individual;
import charles.utils.ScaleToRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Every genotype is multiplied by a (for each genotype) random double between e.g. -0.05 and 0.05.
 * <p>
 * The idea is, that each dimension in the vector space can be on different scales, meaning that
 * the same absolute addition to one dimension can have a smaller/bigger effect than to another dimension.
 * Therefore we add a percentage of the original dimension to that itself.
 * <p>
 * Does not use self-adaptation genotypes to calculate mutation.
 */
public class NoiseMutator implements Mutator {
    private Random rand;
    private List<Double> amountMinimums, amountMaximums;

    public NoiseMutator(Random rand, List<Double> amountMinimums, List<Double> amountMaximums) {
        this.rand = rand;
        this.amountMinimums = amountMinimums;
        this.amountMaximums = amountMaximums;
    }

    public void setAmountMinimums(List<Double> amountMinimums) {
        this.amountMinimums = amountMinimums;
    }

    public void setAmountMaximums(List<Double> amountMaximums) {
        this.amountMinimums = amountMaximums;
    }


    @Override
    public void mutate(Individual individual) {

        ArrayList<double[]> newGenome = new ArrayList<>();

        assert amountMaximums.size() == individual.getNumGenomeArrays();

        for (int g = 0; g < individual.getNumGenomeArrays(); g++) {
            newGenome.add(mutateSingleGenomeArray(individual.getGenome().get(g),
                    amountMinimums.get(g), amountMaximums.get(g)));
        }

        individual.setGenome(newGenome);
    }

    private double[] mutateSingleGenomeArray(double[] genomeArray, double amountMin, double amountMax) {

        double[] newGenomeArray = new double[genomeArray.length];

        for (int gt = 0; gt < genomeArray.length; gt++) {

            // Scale to range. E.g. between -0.1 and 0.1.
            double noiseAmount = ScaleToRange.scaleToRange(
                    rand.nextDouble(), 0.0, 1.0, amountMin, amountMax);

            double currentGenotype = genomeArray[gt];
            newGenomeArray[gt] = currentGenotype + (currentGenotype * noiseAmount);

        }

        return newGenomeArray;

    }
}
