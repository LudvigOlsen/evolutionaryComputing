package charles;

import java.util.ArrayList;

public class Population {

    private ArrayList<Individual> population;
    private double totalFitnessScore = 0;

    public Population() {
        population = new ArrayList<>();
    }

    public void addIndividual(Individual C) {
        population.add(C);
    }

    public void deleteIndividual(int index) {
        population.remove(index);
    }

    public Individual getIndividual(int index) {
        return population.get(index);
    }

    public int getPopulationSize() {
        return population.size();
    }

    /*
    Returns the population ArrayList<Individual>.
     */
    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * Merge current population with another population
     *
     * @param population The population to add to the current population.
     */
    public void merge(Population population) {
        this.population.addAll(population.getPopulation());
    }


    private void calculateTotalFitnessScore() {
        double totalFitnessScore = 0.0;
        for (Individual individual : population) {
            totalFitnessScore += individual.getFitnessScore();
        }

        this.totalFitnessScore = totalFitnessScore;

    }

    /**
     * Calculates and return total fitness score for population.
     */
    public double getTotalFitnessScore() {
        calculateTotalFitnessScore();
        return totalFitnessScore;
    }

}
