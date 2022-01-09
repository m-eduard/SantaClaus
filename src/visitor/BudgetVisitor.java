package visitor;

import command.Visitor;
import entities.Child;
import entities.Santa;
import factory.AverageScoreFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BudgetVisitor implements Visitor {
    /**
     * budget unit, that will be calculated after calling
     * the visit() method on a Santa object
     * (and will be further used for every Child visit)
     */
    private double budgetUnit;

    /**
     * Method that assigns a budget to a child
     * (using the formula: averageScore * santaBudgetUnit)
     *
     * @param child the Child object which have
     *              its budget calculated
     */
    @Override
    public void visit(final Child child) {
        child.setAssignedBudget(child.getAverageScore() * budgetUnit);
    }

    /**
     * Method that:
     *  - calculates the santa's budget unit,
     *    by dividing santa's total budget to the sum
     *    of children's average scores
     *  - assigns a budget to children, by calling
     *    the visit method for every child in santa's lists
     * <p>
     * <b>This method should be called before visiting a child!</b>
     * </p>
     * @param santa Santa object
     */
    @Override
    public void visit(final Santa santa) {
        double sumScoresAverage = 0.0;

        /* Sort the santa's list of children after id, in
         * order to sum the average scores (doubles) in the
         * requested order */
        List<Child> orderedChildren = new ArrayList<>(santa.getChildrenList().values());
        orderedChildren.sort(Comparator.comparingInt(Child::getId));

        for (Child child : orderedChildren) {
            double currentAverageScore = AverageScoreFactory.getInstance()
                    .createStrategy(child.getAgeCategory().name())
                    .getAverageScore(child);

            child.setAverageScore(currentAverageScore);
            sumScoresAverage += currentAverageScore;
        }

        budgetUnit = santa.getBudget() / sumScoresAverage;

        for (Child child : santa.getChildrenList().values()) {
            child.accept(this);
        }
    }
}
