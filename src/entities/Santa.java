package entities;

import enums.Category;
import input.ChildUpdate;

import java.util.List;
import java.util.Map;

public class Santa {
    private double budget;
    private Map<Category, List<Gift>> availableGifts;
    private Map<Integer, Child> childrenList;

    public Santa(final double budget, final Map<Category, List<Gift>> availableGifts,
                 final Map<Integer, Child> childrenList) {
        this.budget = budget;
        this.availableGifts = availableGifts;
        this.childrenList = childrenList;
    }

    public void update(final double newBudget, final List<Gift> newGifts,
                       final List<Child> newChildren, List<ChildUpdate> childrenUpdate) {
        this.budget = newBudget;

        /**
         * Update the children that are on the Santa's list
         */


        /**
         * Add all the new children in its list (represented as Map)
         */
        for (Child child : newChildren) {

        }

        /**
         * Remove the children > 18yo
         */

    }
}
