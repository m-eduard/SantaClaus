package strategy;

import common.Constants;
import entities.Child;

public final class KidAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        double averageScore = child.getNiceScoreHistory().stream()
                .reduce(0.0, Double::sum) / child.getNiceScoreHistory().size();

        /* Add the bonus to the initial average score */
        averageScore += averageScore * child.getNiceScoreBonus() / 100;

        return Math.min(averageScore, Constants.MAX_SCORE);
    }
}
