package strategy;

import command.DistributeGifts;
import command.Invoker;
import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that implements a method to distribute gifts
 * to multiple children, sorted increasingly by their id.
 */
public final class IdDistributionStrategy implements DistributionStrategy {
    @Override
    public void distributeGifts(final Map<Category, List<Gift>> availableGifts,
                                final Map<Integer, Child> children) {
        /* Sort the list of children increasingly after their id-s */
        List<Child> orderedChildren = new ArrayList<>(children.values());
        orderedChildren.sort(Comparator.comparingInt(Child::getId));

        /* Assign the gifts */
        Invoker.getInstance().execute(new DistributeGifts(orderedChildren, availableGifts));
    }
}
