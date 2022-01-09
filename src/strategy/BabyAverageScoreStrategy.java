package strategy;

import common.Constants;
import entities.Child;

public final class BabyAverageScoreStrategy implements AverageScoreStrategy {
    @Override
    public double getAverageScore(final Child child) {
        return Constants.BABY_AVERAGE;
    }
}
