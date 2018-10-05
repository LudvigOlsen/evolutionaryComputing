package charles.migrators;

import charles.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CircularMigrator implements Migrator {


    private Random rand;

    public CircularMigrator(Random rand) {
        this.rand = rand;
    }

    /**
     * Randomly picks n individuals from population (pop) 0 and adds them to pop 1.
     * Then from pop 1 to pop 2 (only original members).
     * The last population adds to population 0 (hence the name).
     * <p>
     * NOTE: Currently only works when the populations have the same size!
     *
     * @param populations     List of populations
     * @param numReplacements Number of individuals to migrate.
     */
    public void migrate(ArrayList<Population> populations, int numReplacements) {
        int numPopulations = populations.size();
        int populationSize = populations.get(0).getPopulationSize();

        for (int p = 0; p < numPopulations; p++) {
            int p2 = p == numPopulations - 1 ? 0 : p + 1; // Add to population 0 if at end
            ArrayList<Integer> indices = sampleIndices(populationSize, numReplacements);
            Population migrants = populations.get(p).pullOut(indices);
            populations.get(p2).merge(migrants);
        }
    }

    private ArrayList<Integer> sampleIndices(int populationSize, int numReplacements) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, rand);
        return new ArrayList<Integer>(indices.subList(0, numReplacements));

    }
}
