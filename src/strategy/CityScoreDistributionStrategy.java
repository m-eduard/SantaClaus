package strategy;

import entities.Child;
import entities.Gift;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import factory.DistributionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class CityScoreDistributionStrategy implements DistributionStrategy {
    @Override
    public void distributeGifts(final Map<Category, List<Gift>> availableGifts,
                                final Map<Integer, Child> children) {
        /* Map that associates every town with its children's id-s */
        Map<Cities, List<Integer>> towns = new HashMap<>();
        /* Map that associates every town with its average score */
        Map<Cities, Double> townsScores = new HashMap<>();

        /* The children are sorted by their id-s, in order to map every child
         * to a town and calculate the towns' sum of nice scores at once
         * (because the order matters when summing double values)  */
        List<Child> sortedChildren = new ArrayList<>(children.values());
        sortedChildren.sort(Comparator.comparingInt(Child::getId));

        for (Child child : sortedChildren) {
            /* Add a new city to the hashmap */
            if (!towns.containsKey(child.getCity())) {
                towns.put(child.getCity(), new ArrayList<>());
                townsScores.put(child.getCity(), 0.0);
            }

            /* Associate the current child's id with his town */
            towns.get(child.getCity()).add(child.getId());
            /* Update the nice scores sum for the child's city */
            townsScores.put(child.getCity(),
                    townsScores.get(child.getCity()) + child.getAverageScore());
        }

        /* Get the average score of every town, by dividing
         * the sum of nice scores to the number of children
         * that are located in that town */
        for (Cities city : townsScores.keySet()) {
            double average = townsScores.get(city) / towns.get(city).size();
            townsScores.put(city, average);
        }

        /* Now, sort the towns by nice score average in descending order */
        List<Cities> sortedTowns = new ArrayList<>(towns.keySet());
        sortedTowns.sort((o1, o2) -> {
            if (Double.compare(townsScores.get(o1), townsScores.get(o2)) == 0) {
                return o1.toString().compareTo(o2.toString());
            }
            return -Double.compare(townsScores.get(o1), townsScores.get(o2));
        });

        /* Start assigning gifts, by using IdDistributionStrategy */
        DistributionStrategy distributor = DistributionFactory.getInstance()
                .createStrategy(CityStrategyEnum.ID);
        for (Cities city : sortedTowns) {
            distributor.distributeGifts(availableGifts, towns.get(city).stream()
                    .collect(Collectors.toMap(id -> id, id -> children.get(id))));
        }
    }
}
