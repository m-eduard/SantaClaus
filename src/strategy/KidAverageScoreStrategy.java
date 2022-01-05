package strategy;

import entities.Child;

import java.util.List;

public class KidAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        return child.getNiceScores().stream().reduce(0.0, Double::sum)
                / child.getNiceScores().size();
    }
}
