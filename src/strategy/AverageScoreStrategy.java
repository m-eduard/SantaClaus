package strategy;

import entities.Child;

public interface AverageScoreStrategy {
    /**
     * Method that returns the average score of a child based
     * on its age category.
     * <p>
     * @param child the Child that will have its score calculated
     * @return the average score as double
     */
    double getAverageScore(Child child);
}
