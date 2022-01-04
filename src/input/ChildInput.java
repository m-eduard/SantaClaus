package input;

import enums.Category;
import enums.Cities;

import java.util.List;

public final class ChildInput {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Cities city;
    private final double niceScore;
    private final List<Category> giftsPreference;

    public ChildInput(final int id, final String firstName,
                      final String lastName, final int age,
                      final Cities city, final double niceScore,
                      final List<Category> giftsPreference) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreference = giftsPreference;
    }
}
