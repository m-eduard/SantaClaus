package strategy;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.List;
import java.util.Map;

public interface DistributionStrategy {
    /**
     * Method that assigns gifts to children, in a specific order
     * <p>
     * @param availableGifts map that links every category to
     *                         the available gifts from that type
     * @param children map where every child is linked to its id
     */
    void distributeGifts(Map<Category, List<Gift>> availableGifts,
                         Map<Integer, Child> children);
}
