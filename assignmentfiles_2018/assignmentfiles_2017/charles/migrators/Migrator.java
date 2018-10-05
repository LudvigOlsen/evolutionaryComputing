package charles.migrators;

import charles.Population;

import java.util.ArrayList;

public interface Migrator {

    public void migrate(ArrayList<Population> populations, int numReplacements);
}
