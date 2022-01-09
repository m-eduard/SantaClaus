package command;

import entities.Child;
import entities.Santa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalculateBudgetUnit implements Command {
    /**
     * The Santa object that receives the command
     */
    private final Santa santa;

    public CalculateBudgetUnit(final Santa santa) {
        this.santa = santa;
    }

    /**
     * Method that calculates the santa's budget
     * unit, by dividing santa's total budget to
     * the sum of children's average scores
     */
    @Override
    public void execute() {
        /* Sort the santa's list of children after id, in
         * order to sum the average scores (doubles) in the
         * requested order */
        List<Child> orderedChildren = new ArrayList<>(santa.getChildrenList().values());
        orderedChildren.sort(Comparator.comparingInt(Child::getId));

        double sumScoresAverage = orderedChildren.stream()
                .map(Child::getAverageScore).reduce(0.0, Double::sum);

        santa.setBudgetUnit(santa.getBudget() / sumScoresAverage);
    }
}
