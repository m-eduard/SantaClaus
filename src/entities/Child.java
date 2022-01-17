package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.AgeCategory;
import enums.Category;
import enums.Cities;
import enums.ElvesType;
import factory.AverageScoreFactory;
import utils.Utils;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a child
 */
public final class Child {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final Cities city;
    private int age;
    private final List<Category> giftsPreferences;
    private double averageScore;
    private final List<Double> niceScoreHistory;
    private double assignedBudget;
    private final List<Gift> receivedGifts;
    @JsonIgnore
    private AgeCategory ageCategory;
    @JsonIgnore
    private int niceScoreBonus;
    @JsonIgnore
    private ElvesType elf;

    private Child(final ChildBuilder builder) {
        this.id = builder.id;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.city = builder.city;
        this.age = builder.age;
        this.giftsPreferences = builder.giftsPreferences;
        this.niceScoreHistory = builder.niceScoreHistory;
        this.receivedGifts = builder.receivedGifts;
        this.ageCategory = builder.ageCategory;
        this.niceScoreBonus = builder.niceScoreBonus;
        this.elf = builder.elf;

        /* Get the initial average score, if the child is younger
         * than a YOUNG_ADULT */
        if (ageCategory != AgeCategory.YOUNG_ADULT) {
            this.averageScore = AverageScoreFactory.getInstance()
                    .createStrategy(ageCategory.name())
                    .getAverageScore(this);
        }
    }

    /* Builder used to set the optional bonus score for a Child */
    public static final class ChildBuilder {
        private final int id;
        private final String lastName;
        private final String firstName;
        private final Cities city;
        private final int age;
        private final List<Category> giftsPreferences;
        private final List<Double> niceScoreHistory;
        private final List<Gift> receivedGifts;
        private final AgeCategory ageCategory;
        private int niceScoreBonus;               // optional
        private final ElvesType elf;

        public ChildBuilder(final int id, final String firstName, final String lastName,
                            final Cities city, final int age, final List<Double> niceScores,
                            final List<Category> preferences, final ElvesType elf) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.city = city;
            this.age = age;
            this.niceScoreHistory = niceScores;
            this.giftsPreferences = preferences;
            this.elf = elf;

            this.ageCategory = Utils.ageToAgeCategory(age);
            this.receivedGifts = new ArrayList<>();
        }

        /**
         * Sets the bonus score of a Child
         * @param bonusScore integer
         * @return current instance, as ChildBuilder object
         */
        public ChildBuilder addBonusScore(final int bonusScore) {
            this.niceScoreBonus = bonusScore;
            return this;
        }

        /**
         * Method that creates a Child object using builder's data
         * @return a Child object
         */
        public Child build() {
            return new Child(this);
        }
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
        this.niceScoreBonus = child.getNiceScoreBonus();
        this.elf = child.getElf();
    }

    /**
     * -- updates the child's age
     * -- updates the child's age category
     * -- recalculates the average score (after a year have passed,
     *    the age category might have changed, so the average score
     *    will be modified)
     * -- empties the receivedGifts list, so a new batch of gifts
     *    can be assigned in the new year
     */
    public void incrementAge() {
        age += 1;
        ageCategory = Utils.ageToAgeCategory(age);

        if (ageCategory != AgeCategory.YOUNG_ADULT) {
            averageScore = AverageScoreFactory.getInstance()
                    .createStrategy(ageCategory.name())
                    .getAverageScore(this);
        }

        receivedGifts.clear();
    }

    /**
     * Method that accepts the visit of a Visitor
     * @param visitor a Visitor object
     */
    public void accept(final Visitor visitor) {
        visitor.visit(this);
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

    public int getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setAverageScore(final double averageScore) {
        this.averageScore = averageScore;
    }

    public void setAssignedBudget(final double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }
}
