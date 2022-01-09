package entities;

import enums.Category;
import visitor.Visitor;

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
     * List of gifts stored by their category
     */
    private final Map<Category, List<Gift>> availableGifts;
    /**
     * Santa's list, represented as a map,
     * where every kid is mapped to its id
     */
    private final Map<Integer, Child> childrenList;

    public Santa(final double budget,
                 final Map<Category, List<Gift>> availableGifts,
                 final Map<Integer, Child> childrenList) {
        this.budget = budget;
        this.availableGifts = availableGifts;
        this.childrenList = childrenList;
    }

    /**
     * Method that accepts the visit of a Visitor
     * @param visitor a Visitor object
     */
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    public double getBudget() {
        return budget;
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
}
