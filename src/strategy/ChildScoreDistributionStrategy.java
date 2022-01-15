package strategy;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class that implements a method to distribute gifts
 * to multiple children, sorted decreasingly by their nice score.
 */
public class ChildScoreDistributionStrategy implements DistributionStrategy {
    @Override
    public void distributeGifts(final Map<Category, List<Gift>> availableGifts,
                                final Map<Integer, Child> children) {
        /* Sort the list of children decreasingly after their average scores */
        List<Child> orderedChildren = new ArrayList<>(children.values());
        orderedChildren.sort(new Comparator<>() {
            @Override
            public int compare(Child o1, Child o2) {
                if (Double.compare(o1.getAverageScore(), o2.getAverageScore()) == 0) {
                    return o1.getId() - o2.getId();
                }

                /* Decreasing order */
                return -Double.compare(o1.getAverageScore(), o2.getAverageScore());
            }
        });

        /* Assign the gifts */
        for (Child child : orderedChildren) {
            double budget = child.getAssignedBudget();

            /* Try to select a gift (the most inexpensive
             * available one) for every preferred category */
            for (Category category : child.getGiftsPreferences()) {
                if (availableGifts.containsKey(category)) {
                    Gift targetGift = null;

                    for (Gift gift : availableGifts.get(category)) {
                        if (gift.getQuantity() == 0) {
                            continue;
                        }

                        if (targetGift == null || gift.getPrice() < targetGift.getPrice()) {
                            targetGift = gift;
                        }
                    }

                    /* The budget is smaller than the price of the gift */
                    if (targetGift == null || Double.compare(targetGift.getPrice(), budget) > 0) {
                        continue;
                    }

                    child.getReceivedGifts().add(targetGift);
                    budget -= targetGift.getPrice();
                    targetGift.setQuantity(targetGift.getQuantity() - 1);
                }
            }
        }
    }
}
