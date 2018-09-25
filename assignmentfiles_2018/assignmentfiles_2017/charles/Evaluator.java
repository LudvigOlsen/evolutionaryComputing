package charles;

import org.vu.contest.ContestEvaluation;

/**
 * Calls evaluation for each individual
 * Updates individuals
 */
public class Evaluator {

    private static int totalNumEvaluations;
    private int numAllowedEvaluations;

    private double currentMaxScore;
    private double allTimeMaxScore;
    private Individual currentBestIndividual;
    private Individual allTimeBestIndividual;
    private ContestEvaluation evaluation_;

    public Evaluator(ContestEvaluation evaluation, int numAllowedEvaluations) {
        this.evaluation_ = evaluation;
        this.numAllowedEvaluations = numAllowedEvaluations;
        totalNumEvaluations = 0;
    }

    public void evaluate(Population population) {

        currentMaxScore = 0.0;

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
            if (individual.wasEvaluated()) {
                updateMaxScore(individual.getFitnessScore(), individual);
                individual.incrementAge();
            } else {

                if (totalNumEvaluations < numAllowedEvaluations) {
                    Double fitness = (Double) evaluation_.evaluate(individual.getRepresentation());
                    individual.setFitnessScore(fitness);
                    updateMaxScore(fitness, individual);
                    totalNumEvaluations++;
                } else {
                    individual.setFitnessScore(0.0);
                }

            }

        }

    }

    private void updateMaxScore(double newScore, Individual individual) {
        if (newScore > currentMaxScore) {
            currentMaxScore = newScore;
            currentBestIndividual = individual;
        }
        if (newScore > allTimeMaxScore) {
            allTimeMaxScore = newScore;
            allTimeBestIndividual = individual;
        }
        
    }

    public double getMaxScore() {
        return currentMaxScore;
    }

    public double getAlltimeMaxScore() {
        return allTimeMaxScore;
    }

    public Individual getBestIndividual() {
        return currentBestIndividual;
    }

    public Individual getAllTimeBestIndividual() {
        return allTimeBestIndividual;
    }

    public int getTotalNumEvaluations() {
        return totalNumEvaluations;
    }

}
