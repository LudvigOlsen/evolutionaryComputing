package charles;

import org.vu.contest.ContestEvaluation;

/**
 * Calls evaluation for each individual
 * Updates individuals
 */
public class Evaluator {

    private double maxScore;
    private Individual bestIndividual;
    private ContestEvaluation evaluation_;

    public Evaluator(ContestEvaluation evaluation) {
        this.evaluation_ = evaluation;
    }

    public void evaluate(Population population) {

        maxScore = 0.0;

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
            if (individual.wasEvaluated()) {
                updateMaxScore(individual.getFitnessScore(), individual);
                individual.incrementAge();
            } else {
                try {
                    Double fitness = (Double) evaluation_.evaluate(individual.getGenome());
                    individual.setFitnessScore(fitness);
                    updateMaxScore(fitness, individual);

                } catch (NullPointerException e) { //TODO figure out, why evaluate return null
                    double fitness = 0.0;
                    individual.setFitnessScore(fitness);

                }

            }

        }

    }

    private void updateMaxScore(double newScore, Individual individual) {
        if (newScore > maxScore) {
            maxScore = newScore;
            bestIndividual = individual;
        }
    }

    public double getMaxScore() {
        return maxScore;
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }

}
