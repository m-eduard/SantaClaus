package strategy;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.*;

/**
 * Class that implements a method to distribute gifts
 * to multiple children, sorted increasingly by their id.
 */
public class IdDistributionStrategy implements DistributionStrategy {
    @Override
    public void distributeGifts(final Map<Category, List<Gift>> availableGifts,
                                final Map<Integer, Child> children) {
        /* Sort the list of children increasingly after their id-s */
        List<Child> orderedChildren = new ArrayList<>(children.values());
        orderedChildren.sort(Comparator.comparingInt(Child::getId));

        /* Assign the gifts */
        for (Child child : orderedChildren) {
            double budget = child.getAssignedBudget();

            /* Try to select a gift (the most inexpensive
             * one) for every preferred category */
            for (Category category : child.getGiftsPreferences()) {
                if (availableGifts.containsKey(category)) {
                    Gift targetGift = availableGifts.get(category).stream()
                            .min(Comparator.comparingDouble(Gift::getPrice))
                            .orElseThrow(NoSuchElementException::new);

                    /* The budget is smaller than the price of the gift */
                    if (Double.compare(targetGift.getPrice(), budget) > 0) {
                        continue;
                    }

                    child.getReceivedGifts().add(targetGift);
                    budget -= targetGift.getPrice();
                }
            }
        }
    }
}
