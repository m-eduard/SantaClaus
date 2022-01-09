package entities;

import enums.Category;

import java.util.List;

public final class ChildUpdate {
    private final int id;
    private final Double niceScore;
    private final List<Category> giftsPreferences;

    public ChildUpdate(final int id, final Double niceScore,
                            final List<Category> giftsPreference) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreference;
    }

    public int getId() {
        return id;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }
}
