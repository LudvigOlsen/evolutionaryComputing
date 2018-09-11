package charles;

import java.util.ArrayList;

public interface Recombinator {
    public Individual combine(ArrayList<Individual> parents);
}