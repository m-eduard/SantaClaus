package command;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.List;
import java.util.Map;

public class DistributeGifts implements Command {
    /**
     * List of ordered children
     */
    private final List<Child> children;
    /**
     * Santa's list of available gifts
     */
    private final Map<Category, List<Gift>> gifts;

    public DistributeGifts(final List<Child> children,
                           final Map<Category, List<Gift>> gifts) {
        this.children = children;
        this.gifts = gifts;
    }

    /**
     * Method that assigns gifts from Santa's list,
     * according to the way children are sorted in
     * the provided children list
     */
    @Override
    public void execute() {
        for (Child child : children) {
            double budget = child.getAssignedBudget();

            /* Try to select a gift (the most inexpensive
             * available one) for every preferred category */
            for (Category category : child.getGiftsPreferences()) {
                if (gifts.containsKey(category)) {
                    Gift targetGift = null;

                    for (Gift gift : gifts.get(category)) {
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
