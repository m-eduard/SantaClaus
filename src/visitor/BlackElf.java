package visitor;

import entities.Child;

public class BlackElf implements Visitor {
    @Override
    public void visit(Child child) {
        double currentBudget = child.getAssignedBudget();
        currentBudget -= currentBudget * 30 / 100;

        child.setAssignedBudget(currentBudget);
    }
}
