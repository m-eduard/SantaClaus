package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import command.CalculateBudgetUnit;
import command.CalculateChildBudget;
import command.Invoker;
import command.UpdateSanta;
import entities.Child;
import entities.Gift;
import entities.Santa;
import enums.AgeCategory;
import enums.Category;
import enums.CityStrategyEnum;
import enums.ElvesType;
import factory.DistributionFactory;
import input.AnnualChangeInput;
import input.Input;
import strategy.DistributionStrategy;
import entities.ChildUpdate;
import utils.Utils;
import visitor.*;


/**
 * Class that runs the simulation for the given input
 * (implemented as singleton)
 */
public final class Simulation {
    private int numberOfYears;
    private List<Double> santaBudgets;
    private List<List<Gift>> newGifts;
    private List<List<Child>> newChildren;
    private List<List<ChildUpdate>> childrenUpdates;
    private List<CityStrategyEnum> distributionUpdates;
    private Santa santa;

    private static Simulation instance = null;

    private Simulation() { }

    /**
     * Method that loads the parsed input data in classes that will be
     * used alongside the simulation
     * @param input Input object that stores the data read from input files
     * @return Simulation instance, further used to simulate the Christmas
     *         without a proper Santa
     */
    public static Simulation getInstance(final Input input) {
        if (instance == null) {
            instance = new Simulation();
        }

        instance.numberOfYears = input.getNumberOfYears();

        /* Create a Map that stores for every category
         * of gifts a bucket with the available gifts */
        Map<Category, List<Gift>> availableGifts = new HashMap<>();
        input.getInitialData().getGifts().forEach(gift -> {
            if (!availableGifts.containsKey(gift.getCategory())) {
                availableGifts.put(gift.getCategory(), new ArrayList<>());
            }
            availableGifts.get(gift.getCategory())
                    .add(new Gift(gift.getProductName(), gift.getPrice(),
                            gift.getCategory(), gift.getQuantity()));
        });

        /* Put in Santa's list only the children that have less than 18 years */
        instance.santa = new Santa(input.getSantaBudget(), availableGifts,
                input.getInitialData().getChildren().stream()
                        .filter(x -> Utils.ageToAgeCategory(x.getAge()) != AgeCategory.YOUNG_ADULT)
                        .collect(Collectors.toMap(x -> x.getId(), x ->
                                new Child.ChildBuilder(x.getId(), x.getFirstName(),
                                        x.getLastName(), x.getCity(), x.getAge(),
                                        new ArrayList<>(List.of(x.getNiceScore())),
                                        x.getGiftsPreferences(), x.getElf())
                                .addBonusScore(x.getNiceScoreBonus()).build())));

        /* Create a list of Santa's new yearly budgets */
        instance.santaBudgets = input.getAnnualChanges().stream()
                .map(AnnualChangeInput::getNewSantaBudget).collect(Collectors.toList());

        /* Create a list of the new gifts added in every year,
         * converting GiftInput objects into Gift objects */
        instance.newGifts = input.getAnnualChanges().stream()
                .map(x -> x.getNewGifts().stream()
                        .map(y -> new Gift(y.getProductName(), y.getPrice(),
                                y.getCategory(), y.getQuantity()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        /* Create a list of the new children added to Santa's list
         * in every year (ChildInput is converted into Child) */
        instance.newChildren = input.getAnnualChanges().stream()
                .map(x -> x.getNewChildren().stream()
                        .map(y -> new Child.ChildBuilder(y.getId(), y.getFirstName(),
                                    y.getLastName(), y.getCity(), y.getAge(),
                                    new ArrayList<>(List.of(y.getNiceScore())),
                                    y.getGiftsPreferences(), y.getElf())
                                .addBonusScore(y.getNiceScoreBonus()).build())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        /* Create a list of yearly children updates */
        instance.childrenUpdates = input.getAnnualChanges().stream()
                .map(x -> x.getChildrenUpdates().stream()
                        .map(y -> new ChildUpdate(y.getId(),
                                y.getNiceScore(), y.getGiftsPreference(),
                                y.getElf()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        /* Create a list of distribution strategies that will be used */
        instance.distributionUpdates = input.getAnnualChanges().stream()
                .map(AnnualChangeInput::getDistributionStrategy)
                .collect(Collectors.toList());

        return instance;
    }

    /**
     * Method that runs the simulation for all the years,
     * and keeps the track of the changes that occur for every child
     *
     * @param mapper ObjectMapper, used as mapper for JSONs
     * @param childrenJsonArray head of an array used to store in every year
     *                          the current state for the children in Santa's
     *                          list, ordered by their id-s
     */
    public void simulateAllYears(final ObjectMapper mapper,
                                 final ArrayNode childrenJsonArray) {
        System.out.println("-------------------------------------------------");
        /* elves */
        Visitor yellowElf = new YellowElf(santa.getAvailableGifts());
        Visitor blackElf = new BlackElf();
        Visitor pinkElf = new PinkElf();
        Visitor whiteElf = new WhiteElf();

        /* Invoker for the commands applied on Santa/Child */
        Invoker invoker = Invoker.getInstance();
        invoker.restart();

        /* Every iteration of the for represents a round of simulation
         * (i starts from -1, because the first round is using the initial
         * data, and is not linked to the annual updates that will occur
         * for <numberOfYears> years) */
        for (int i = -1; i < numberOfYears; ++i) {
            /* For the first simulation (when i == -1),
             * no data has to be updated, so the update
             * command is executed only for other iterations */
            if (i >= 0) {
                invoker.execute(new UpdateSanta(santa,
                        santaBudgets.get(i), newGifts.get(i),
                        newChildren.get(i), childrenUpdates.get(i),
                        distributionUpdates.get(i)));
            }

            invoker.execute(new CalculateBudgetUnit(santa));
            for (Child child : santa.getChildrenList().values()) {
                invoker.execute(new CalculateChildBudget(child, santa.getBudgetUnit()));
            }

            /* Update the budget for children that have a black/pink elf */
            for (Child child : santa.getChildrenList().values()) {
                if (child.getElf() == ElvesType.PINK) {
                    child.accept(pinkElf);
                } else if (child.getElf() == ElvesType.BLACK) {
                    child.accept(blackElf);
                }
            }

            /* Strategy used to distribute the available gifts
             * between all the children, based on the current year
             * specified strategy */
            DistributionStrategy distributor = DistributionFactory.getInstance()
                    .createStrategy(santa.getDistributionStrategy());
            distributor.distributeGifts(santa.getAvailableGifts(),
                    santa.getChildrenList());

            /* Children are visited by the yellow elf,
             *  if no gift was assigned to them */
            for (Child child : santa.getChildrenList().values()) {
                if (child.getElf() == ElvesType.YELLOW) {
                    child.accept(yellowElf);
                }
            }

            /* List that will store the output data for one year */
            List<Child> yearChildrenList = new ArrayList<>();

            List<Child> orderedChildren = new ArrayList<>(santa.getChildrenList().values());
            orderedChildren.sort(Comparator.comparingInt(Child::getId));

            /* Make a copy of children's current data, storing the copies sorted by id-s */
            orderedChildren.forEach(child -> yearChildrenList.add(new Child(child)));
            childrenJsonArray.add(mapper.createObjectNode().putPOJO("children", yearChildrenList));
        }
    }
}
