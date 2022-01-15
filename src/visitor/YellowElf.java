package visitor;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class YellowElf implements Visitor {
    /**
     * Santa's gift list
     */
    private final Map<Category, List<Gift>> availableGifts;

    public YellowElf(final Map<Category, List<Gift>> availableGifts) {
        this.availableGifts = availableGifts;
    }

    @Override
    public void visit(Child child) {
        /* Check if the child received any gifts at all */
        if (child.getReceivedGifts().size() > 0) {
            return;
        }

        /* Try to select a gift (the most inexpensive
         * one) for every preferred category */
        Category mostWantedCategory = child.getGiftsPreferences().get(0);
        if (availableGifts.containsKey(mostWantedCategory)) {
            Gift targetGift = availableGifts.get(mostWantedCategory).stream()
                    .min(Comparator.comparingDouble(Gift::getPrice))
                    .orElseThrow(NoSuchElementException::new);

            /* If the targetGift is available, it is assigned
             * to the child */
            if (targetGift.getQuantity() > 0) {
                child.getReceivedGifts().add(targetGift);
                targetGift.setQuantity(targetGift.getQuantity() - 1);
            }
        }
    }
}
