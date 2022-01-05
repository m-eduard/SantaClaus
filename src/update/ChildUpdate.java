package update;

import enums.Category;

import java.util.List;

public class ChildUpdate {
    private final int id;
    private final Double niceScore;
    private final List<Category> giftsPreference;

    public ChildUpdate(final int id, final Double niceScore,
                            final List<Category> giftsPreference) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreference = giftsPreference;
    }

    public int getId() {
        return id;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public List<Category> getGiftsPreference() {
        return giftsPreference;
    }
}
