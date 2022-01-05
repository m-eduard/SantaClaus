package strategy;

import entities.Child;

import java.util.List;

public class TeenAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        List<Double> scores = child.getNiceScores();

        double averageScore = 0.0;
        for (int i = 0; i <  scores.size(); ++i) {
            averageScore += scores.get(i) * (i + 1);
        }

        return (averageScore * 2) / (scores.size() * (scores.size() + 1));
    }
}
