package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.AgeCategory;
import enums.Category;
import enums.Cities;
import utils.Utils;
import command.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a child
 */
public final class Child {
    private int id;
    private String lastName;
    private String firstName;
    private final Cities city;
    private int age;
    private final List<Category> giftsPreferences;
    private double averageScore;
    private final List<Double> niceScoreHistory;
    private double assignedBudget;
    private final List<Gift> receivedGifts;
    @JsonIgnore
    private AgeCategory ageCategory;

    public Child(final int id, final String firstName, final String lastName,
                 final Cities city, final int age, final List<Double> niceScores,
                 final List<Category> preferences) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.niceScoreHistory = niceScores;
        this.giftsPreferences = preferences;

        this.ageCategory = Utils.ageToAgeCategory(age);
        this.receivedGifts = new ArrayList<>();
    }

    /**
     * Copy constructor
     */
    public Child(final Child child) {
        this.id = child.getId();
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.city = child.getCity();
        this.age = child.getAge();
        this.niceScoreHistory = new ArrayList<>(child.getNiceScoreHistory());
        this.averageScore = child.getAverageScore();
        this.assignedBudget = child.getAssignedBudget();
        this.giftsPreferences = new ArrayList<>(child.getGiftsPreferences());
        this.ageCategory = child.getAgeCategory();
        this.receivedGifts = new ArrayList<>(child.getReceivedGifts());
    }

    /**
     * Method that accepts the visit of a Visitor
     * @param visitor a Visitor object
     */
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Updates the child's age and empties the receivedGifts list,
     * so a new batch of gifts can be assigned in the new year.
     */
    public void incrementAge() {
        age += 1;
        ageCategory = Utils.ageToAgeCategory(age);

        receivedGifts.clear();
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Cities getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public double getAssignedBudget() {
        return assignedBudget;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAverageScore(final double averageScore) {
        this.averageScore = averageScore;
    }

    public void setAssignedBudget(final double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }
}
