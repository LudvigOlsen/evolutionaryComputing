package charles;

import org.vu.contest.ContestEvaluation;

/**
 * Calls evaluation for each individual
 * Updates individuals
 */
public class Evaluator {

    private double maxScore;
    private ContestEvaluation evaluation_;

    public Evaluator(ContestEvaluation evaluation) {
        this.evaluation_ = evaluation;
    }

    public void evaluate(Population population) {

        maxScore = 0.0;

        for (int i = 0; i < population.getPopulationSize(); i++) {

            Individual individual = population.getIndividual(i);
            if (individual.wasEvaluated()) {
                updateMaxScore(individual.getFitnessScore());
                individual.incrementAge();
            } else {
                double fitness = (double) evaluation_.evaluate(individual.getGenome());
                individual.setFitnessScore(fitness);
                updateMaxScore(fitness);

            }

        }

    }

    private void updateMaxScore(double newScore) {
        if (newScore > maxScore) maxScore = newScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

}
