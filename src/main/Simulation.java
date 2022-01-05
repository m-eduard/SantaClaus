package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Child;
import entities.Gift;
import entities.Santa;
import enums.AgeCategory;
import enums.Category;
import input.AnnualChangeInput;
import input.Input;
import strategy.DistributionStrategy;
import strategy.IdDistributionStrategy;
import update.ChildUpdate;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that runs the simulation for the given input
 * (Implemented as singleton)
 */
public class Simulation {
    private int numberOfYears;
    private List<Double> santaBudgets;
    private List<List<Gift>> newGifts;
    private List<List<Child>> newChildren;
    private List<List<ChildUpdate>> childrenUpdates;
    private Santa santa;

    private static Simulation instance = null;

    private Simulation() {}

    public static Simulation getInstance(Input input) {
        if (instance == null)
            instance = new Simulation();

        /**
         * Load the data stored in input
         */

        instance.numberOfYears = input.getNumberOfYears();

        /**
         * Create a Map that stores for every category
         * of gifts a bucket with the available gifts
         */
        Map<Category, List<Gift>> availableGifts = new HashMap<>();
        input.getInitialData().getGifts().forEach(gift -> {
            if (!availableGifts.containsKey(gift.getCategory())) {
                availableGifts.put(gift.getCategory(), new ArrayList<>());
            }
            availableGifts.get(gift.getCategory()).add(new Gift(gift.getProductName(),
                    gift.getPrice(), gift.getCategory()));
        });

        /**
         * Put in Santa's list only the children that have less than 18 years
         */
        instance.santa = new Santa(input.getSantaBudget(), availableGifts,
                input.getInitialData().getChildren().stream()
                        .filter(x -> Utils.ageToAgeCategory(x.getAge()) != AgeCategory.YOUNG_ADULT)
                        .collect(Collectors.toMap(x -> x.getId(), x ->
                                new Child(x.getId(), x.getFirstName(), x.getLastName(), x.getCity(), x.getAge(),
                                        new ArrayList<>(Arrays.asList(x.getNiceScore())),
                                        x.getGiftsPreference()))));

        instance.santaBudgets = input.getAnnualChanges().stream()
                .map(AnnualChangeInput::getNewSantaBudget).collect(Collectors.toList());
        instance.newGifts = input.getAnnualChanges().stream()
                .map(x -> x.getNewGifts().stream()
                        .map(y -> new Gift(y.getProductName(), y.getPrice(), y.getCategory()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        instance.newChildren = input.getAnnualChanges().stream()
                .map(x -> x.getNewChildren().stream()
                        .map(y -> new Child(y.getId(), y.getFirstName(),
                                y.getLastName(), y.getCity(), y.getAge(),
                                new ArrayList<>(Arrays.asList(y.getNiceScore())),
                                y.getGiftsPreference()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        instance.childrenUpdates = input.getAnnualChanges().stream()
                .map(x -> x.getChildrenUpdates().stream()
                        .map(y -> new ChildUpdate(y.getId(), y.getNiceScore(), y.getGiftsPreference()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return instance;
    }

    public static Simulation getInstance() {
        if (instance == null)
            instance = new Simulation();
        return instance;
    }

    public void simulateAllYears(final String outputPath) throws IOException {
        /**
         * Strategy used to distribute the available gifts
         * between all the children
         */
        DistributionStrategy distributor = new IdDistributionStrategy();

        ObjectMapper mapper = new ObjectMapper();

        santa.setChildrenBudgets();
        distributor.distributeGifts(santa.getAvailableGifts(),
                santa.getChildrenList());

        List<Child> out = new ArrayList<>();
        for (Child child : santa.getChildrenList().values()) {
            out.add(new Child(child));
        }

        ObjectNode annualChildren = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(mapper.createObjectNode().putPOJO("children", out));

        for (int i = 0; i < numberOfYears; ++i) {
            santa.update(santaBudgets.get(i), newGifts.get(i),
                    newChildren.get(i), childrenUpdates.get(i));
            santa.setChildrenBudgets();
            distributor.distributeGifts(santa.getAvailableGifts(),
                    santa.getChildrenList());

            List<Child> out1 = new ArrayList<>();
            List<Child> orderedChildren = new ArrayList<>(santa.getChildrenList().values());
            orderedChildren.sort(Comparator.comparingInt(Child::getId));
            for (Child child : orderedChildren)
                out1.add(new Child(child));

            arrayNode.add(mapper.createObjectNode().putPOJO("children", out1));
        }

        annualChildren.putPOJO("annualChildren", arrayNode);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputPath), annualChildren);
    }
}
