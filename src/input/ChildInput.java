package input;

import enums.Category;
import enums.Cities;

import java.util.List;

/**
 * Information about a child, retrieved from parsing the input files
 */
public final class ChildInput {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final int age;
    private final Cities city;
    private final double niceScore;
    private final List<Category> giftsPreferences;
    private final int niceScoreBonus;

    public ChildInput(final int id, final String lastName,
                      final String firstName, final int age,
                      final Cities city, final double niceScore,
                      final List<Category> giftsPreference,
                      final int niceScoreBonus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreference;
        this.niceScoreBonus = niceScoreBonus;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Cities getCity() {
        return city;
    }

    public double getNiceScore() {
        return niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public int getNiceScoreBonus() {
        return niceScoreBonus;
    }
}
