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

}
