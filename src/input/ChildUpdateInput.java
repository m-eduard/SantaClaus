package input;

import enums.Category;

import java.util.List;

public class ChildUpdateInput {
    private final int id;
    private final Double niceScore;
    private final List<Category> giftsPreference;

    public ChildUpdateInput(final int id, final Double niceScore,
                            final List<Category> giftsPreference) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreference = giftsPreference;
    }
}
