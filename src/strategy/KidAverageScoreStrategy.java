package strategy;

import entities.Child;

public final class KidAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        return child.getNiceScoreHistory().stream().reduce(0.0, Double::sum)
                / child.getNiceScoreHistory().size();
    }
}
