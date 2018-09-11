package charles;

public class Individual implements Comparable<Individual> {

    private double genome[];
    private double fitnessScore = 0.0;
    private Boolean wasEvaluatedFlag = false;

    public Individual(double[] genome) {
        this.genome = genome;
    }

    public double[] getGenome() {
        return genome;
    }

    public void setGenome(double[] genome) {
        this.genome = genome;
    }

    public int getGenomeSize() {
        return this.genome.length;
    }

    public void setFitnessScore(double score) {
        fitnessScore = score;
        wasEvaluatedFlag = true;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public Boolean wasEvaluated() {
        return wasEvaluatedFlag;
    }

    public int compareTo(Individual individual) {
        return Double.compare(fitnessScore, individual.getFitnessScore());
    }

}
