package charles;

public class Individual {

    private double genome[];

    public Individual(double[] genome) {
        this.genome = genome;
    }

    public double[] getGenome() {
        return genome;
    }

    public void setGenotype(double[] genome) {
        this.genome = genome;
    }

}
