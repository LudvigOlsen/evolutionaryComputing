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

    private final NavigableMap<Double, Individual> map = new TreeMap<Double, Individual>();
    private final Random random;
    private double totalWeight = 0;

    public WeightedSampler() {
        this.random = new Random();
    }

    public WeightedSampler<Individual> add(double weight, Individual individual) {
        if (weight <= 0) return this;
        totalWeight += weight;
        map.put(totalWeight, individual);
        return this;
    }

    /**
     * Pick next individual
     *
     * @return Individual
     */
    public Individual next() {
        double value = random.nextDouble() * totalWeight;
        return map.higherEntry(value).getValue();
    }

}
