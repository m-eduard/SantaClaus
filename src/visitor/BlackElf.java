package visitor;

import common.Constants;
import entities.Child;

public final class BlackElf implements Visitor {
    @Override
    public void visit(final Child child) {
        double currentBudget = child.getAssignedBudget();
        currentBudget -= currentBudget * Constants.BONUS_ELF / Constants.CENT;

        child.setAssignedBudget(currentBudget);
    }
}
