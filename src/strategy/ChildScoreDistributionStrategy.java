package strategy;

import command.DistributeGifts;
import command.Invoker;
import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that implements a method to distribute gifts
 * to multiple children, sorted decreasingly by their nice score.
 */
public final class ChildScoreDistributionStrategy implements DistributionStrategy {
    @Override
    public void distributeGifts(final Map<Category, List<Gift>> availableGifts,
                                final Map<Integer, Child> children) {
        /* Sort the list of children decreasingly after their average scores */
        List<Child> orderedChildren = new ArrayList<>(children.values());
        orderedChildren.sort((o1, o2) -> {
            if (Double.compare(o1.getAverageScore(), o2.getAverageScore()) == 0) {
                return o1.getId() - o2.getId();
            }

            /* Decreasing order */
            return -Double.compare(o1.getAverageScore(), o2.getAverageScore());
        });

        /* Assign the gifts */
        Invoker.getInstance().execute(new DistributeGifts(orderedChildren, availableGifts));
    }
}
