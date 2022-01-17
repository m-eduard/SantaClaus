package strategy;

import common.Constants;
import entities.Child;

import java.util.List;

public final class TeenAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        List<Double> scores = child.getNiceScoreHistory();

        double averageScore = 0.0;
        for (int i = 0; i <  scores.size(); ++i) {
            averageScore += scores.get(i) * (i + 1);
        }

        /* averageScore / (1 + 2 + ... + scores.size()), but denominator sum
         * is written as Gaussian sum */
        averageScore = (averageScore * 2) / (scores.size() * (scores.size() + 1));

        /* Add the bonus */
        averageScore += averageScore * child.getNiceScoreBonus() / Constants.CENT;

        return Math.min(averageScore, Constants.MAX_SCORE);
    }
}
