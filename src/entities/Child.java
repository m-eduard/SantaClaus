package entities;

import enums.AgeCategory;
import enums.Category;
import utils.Utils;

import java.util.List;

public class Child {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private final List<Double> niceScores;
    private final List<Category> preferences;
    private AgeCategory ageCategory;

    public Child(final int id, final String firstName, final String lastName,
                 final int age, final List<Double> niceScores, final List<Category> preferences) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.niceScores = niceScores;
        this.preferences = preferences;

        this.ageCategory = Utils.ageToAgeCategory(age);
//        this.averageScoreStrategy = AverageScoreFactory.getInstance().createStrategy(ageCategory);
    }

    public void incrementAge() {
        age += 1;
    }

    public void update(Double newScore, List<Category> newPreferences) {
        ageCategory = Utils.ageToAgeCategory(age);

        /**
         * Insert the new gift preferences into the list starting from
         * the least important one to the most important, such as in the
         * end, the list will have the preferences in decreasing order after preference
         */
        for (int i = newPreferences.size() - 1; i >= 0; --i) {
            /**
             * Try to remove the preference, if it already exists in the list,
             * in order to insert it again at the beginning of the list (higher priority)
             */
            preferences.remove(newPreferences.get(i));
            preferences.add(0, newPreferences.get(i));
        }

        if (newScore != null) {
            niceScores.add(newScore);
        }
    }

    public List<Double> getNiceScores() {
        return niceScores;
    }
}
