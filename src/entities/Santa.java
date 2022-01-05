package entities;

import enums.AgeCategory;
import enums.Category;
import factory.AverageScoreFactory;
import update.ChildUpdate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Information about Santa
 */
public class Santa {
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
                 final Map<Category,List<Gift>> availableGifts,
                 final Map<Integer, Child> childrenList) {
        this.budget = budget;
        this.availableGifts = availableGifts;
        this.childrenList = childrenList;
    }

    /**
     * Updates Santa's resources for the new year
     * and also updates the info for the children
     * that are still on its list.
     * @param newBudget new budget as double
     * @param newGifts list of gifts
     * @param newChildren list of children
     * @param childrenUpdate list of updates for the current year
     */
    public void update(final double newBudget, final List<Gift> newGifts,
                       final List<Child> newChildren,
                       final List<ChildUpdate> childrenUpdate) {
        this.budget = newBudget;

        /* Update the children that are on the Santa's list */
        for (int id : childrenList.keySet()) {
            childrenList.get(id).incrementAge();
        }

        /* Add all the new children in its list (represented as Map) */
        for (Child child : newChildren) {
            childrenList.put(child.getId(), child);
        }

        /* Remove the children > 18yo, by gathering their id-s
         * and deleting the corresponding entries from the map
         * */
        List<Integer> idsToRemove = new ArrayList<>();
        for (int id : childrenList.keySet()) {
            if (childrenList.get(id).getAgeCategory() == AgeCategory.YOUNG_ADULT) {
                idsToRemove.add(id);
            }
        }
        idsToRemove.forEach(childrenList::remove);

        /* Update the info for the children that are still on Santa's list */
        for (ChildUpdate childUpdate : childrenUpdate) {
            if (childrenList.containsKey(childUpdate.getId())) {
                childrenList.get(childUpdate.getId())
                        .update(childUpdate.getNiceScore(), childUpdate.getGiftsPreference());
            }
        }
    }

    /**
     * Calculates and assigns the budget to every child
     */
    public void setChildrenBudgets() {
        /* Get the total average score, by summing the
         * children's scores, sorted by their id-s
         * */
        Double totalAverage = 0.0;
        List<Child> orderedChildren = new ArrayList<>(childrenList.values());
        orderedChildren.sort(Comparator.comparingInt(Child::getId));

        for (Child child : orderedChildren) {
            double currentAverageScore = AverageScoreFactory.getInstance()
                    .createStrategy(child.getAgeCategory())
                    .getAverageScore(child);

            child.setAverageScore(currentAverageScore);
            totalAverage += currentAverageScore;
        }

        /* Use the formula (avgScore * (santaBudget / sumAvgScore))
         * to get the budget assigned by Santa to every child.
         * */
        for (Child child : orderedChildren) {
            child.setAssignedBudget(child.getAverageScore() * (budget / totalAverage));
        }
    }

    public Map<Category, List<Gift>> getAvailableGifts() {
        return availableGifts;
    }

    public Map<Integer, Child> getChildrenList() {
        return childrenList;
    }
}
