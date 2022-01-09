package command;

import entities.Child;

public class CalculateChildBudget implements Command {
    /**
     * The Child object which have its budget calculated
     */
    private final Child child;
    /**
     * The budget unit value, as double
     */
    private final double budgetUnit;

    public CalculateChildBudget(final Child child, final double budgetUnit) {
        this.child = child;
        this.budgetUnit = budgetUnit;
    }

    /**
     * Method that assigns a budget to a child
     * (using the formula: averageScore * santaBudgetUnit)
     */
    @Override
    public void execute() {
        child.setAssignedBudget(child.getAverageScore() * budgetUnit);
    }
}
