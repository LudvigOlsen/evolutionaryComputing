package charles.utils;

import charles.Individual;

import java.util.HashMap;
import java.util.Random;


public class WeightedSampler {

    private HashMap<Integer, Double> weights; // id => weight
    private HashMap<Integer, Individual> individuals; // id => individual
    private double totalWeight = 0.0;
    private Random rand;


    public WeightedSampler(Random rand) {
        weights = new HashMap<>();
        individuals = new HashMap<>();
        this.rand = rand;
    }

    public void add(Individual individual, double weight) {
        if (weight >= 0) {
            weights.put(individual.getId(), weight);
            individuals.put(individual.getId(), individual);
        } else {
            System.out.println(weight);
            throw new IllegalArgumentException("weight cannot be below 0.0.");
        }
    }

    public void clearSampler() {
        weights.clear();
        individuals.clear();
        totalWeight = 0.0;
    }

    public int getSamplerSize() {
        return weights.size();
    }

    public Boolean contains(Individual individual) {
        return weights.containsKey(individual.getId());
    }

    private double getWeight(int id) {
        return weights.get(id);
    }

    public Individual next() {

        totalWeight = 0.0;

        for (int id : weights.keySet()) {
            totalWeight += getWeight(id);
        }

        double randomNumber = rand.nextDouble() * totalWeight;
        double cumWeight = 0.0;
        int pickedId;
        Individual pickedIndividual;
        for (int id : weights.keySet()) {
            cumWeight += getWeight(id);
            if (cumWeight >= randomNumber) {
                pickedId = id;
                pickedIndividual = individuals.get(pickedId);
                weights.remove(pickedId);
                individuals.remove(pickedId);
                return pickedIndividual;
            }
        }
        throw new RuntimeException("Should never be shown.");
    }
}