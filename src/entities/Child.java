package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.AgeCategory;
import enums.Category;
import enums.Cities;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a child
 */
public class Child {
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
     * Updates the child's age and empties the receivedGifts list,
     * so a new batch of gifts can be assigned in the new year.
     */
    public void incrementAge() {
        age += 1;
        ageCategory = Utils.ageToAgeCategory(age);

        receivedGifts.clear();
    }

    /**
     * Updates the child's info with new data, gathered from the last year.
     * @param newScore new year's score
     * @param newPreferences new year's preferences
     */
    public void update(Double newScore, List<Category> newPreferences) {
        ageCategory = Utils.ageToAgeCategory(age);

        /* Insert the new gift preferences into the list,
         * pushing consecutively the least important ones
         * on the first position, so in the end, the list will
         * store the preferences decreasingly after priority
         * */
        for (int i = newPreferences.size() - 1; i >= 0; --i) {
            /* Remove the preference, if it already exists,
             * in order to insert it again, at the beginning
             * of the list (with higher priority)
             * */
            giftsPreferences.remove(newPreferences.get(i));
            giftsPreferences.add(0, newPreferences.get(i));
        }

        if (newScore != null) {
            niceScoreHistory.add(newScore);
        }
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

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setAssignedBudget(double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }
}
