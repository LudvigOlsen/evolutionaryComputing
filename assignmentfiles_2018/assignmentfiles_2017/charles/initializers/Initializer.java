package charles.initializers;

import charles.Population;

import java.util.List;

public interface Initializer {
    
    public Population initialize(int numIndividuals,
                                 List<Integer> genomeSizes,
                                 List<Double> minLimits,
                                 List<Double> maxLimits);
}
