package charles.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;


/**
 * Sampler for creating a custom probability distribution
 * to select individuals from.
 * <p>
 * Based on https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
 */
public class WeightedSampler<Individual> {

    private final NavigableMap<Double, Individual> weightToIndividualMap = new TreeMap<Double, Individual>();
    private final Random random;
    private double totalWeight = 0;

    public WeightedSampler() {
        this.random = new Random();
    }

    public void add(double weight, Individual individual) {
        if (weight > 0) {
            totalWeight += weight;
            weightToIndividualMap.put(totalWeight, individual);
        }
    }

    /**
     * Pick next individual
     *
     * @return Individual
     */
    public Individual next() {
        double value = random.nextDouble() * totalWeight;
        return weightToIndividualMap.higherEntry(value).getValue();
    }

}
