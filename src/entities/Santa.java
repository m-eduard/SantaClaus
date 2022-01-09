package entities;

import enums.Category;

import java.util.List;
import java.util.Map;

/**
 * Information about Santa
 */
public final class Santa {
    /**
     * Current Santa's budget
     */
    private double budget;
    /**
     * Budget unit (will be calculated based on the
     * average scores of the children in Santa's list)
     */
    private double budgetUnit;
    /**
     * List of gifts stored by their category
     */
    private final Map<Category, List<Gift>> availableGifts;
    /**
     * Santa's list, represented as a map,
     * where every kid is mapped to his id
     */
    private final Map<Integer, Child> childrenList;

    public Santa(final double budget,
                 final Map<Category, List<Gift>> availableGifts,
                 final Map<Integer, Child> childrenList) {
        this.budget = budget;
        this.availableGifts = availableGifts;
        this.childrenList = childrenList;
    }

    public double getBudget() {
        return budget;
    }

    public double getBudgetUnit() {
        return budgetUnit;
    }

    public Map<Category, List<Gift>> getAvailableGifts() {
        return availableGifts;
    }

    public Map<Integer, Child> getChildrenList() {
        return childrenList;
    }

    public void setBudget(final double budget) {
        this.budget = budget;
    }

    public void setBudgetUnit(final double budgetUnit) {
        this.budgetUnit = budgetUnit;
    }
}
