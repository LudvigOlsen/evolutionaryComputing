package charles;

import java.util.ArrayList;

public class Population {

    private ArrayList<Individual> population;

    public Population() {
        population = new ArrayList<>();
    }

    public void addChild(Individual C) {
        population.add(C);
    }

    public void deleteChild(int index) {
        population.remove(index);
    }

    public Individual getChild(int index) {
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

}
