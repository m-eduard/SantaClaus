package visitor;

import common.Constants;
import entities.Child;

public final class PinkElf implements Visitor {
    /**
     * Increases child's assigned budget by 30%.
     * @param child Child object
     */
    @Override
    public void visit(final Child child) {
        double currentBudget = child.getAssignedBudget();
        currentBudget += currentBudget * Constants.BONUS_ELF / Constants.CENT;

        child.setAssignedBudget(currentBudget);
    }
}
