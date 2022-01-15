package input;

import enums.Category;
import enums.ElvesType;

import java.util.List;

/**
 * Useful data about an annual child update
 */
public final class ChildUpdateInput {
    private final int id;
    /**
     * Child's new nice score (can be null)
     */
    private final Double niceScore;
    private final List<Category> giftsPreference;
    /**
     * The new elf assigned
     */
    private final ElvesType elf;

    public ChildUpdateInput(final int id, final Double niceScore,
                            final List<Category> giftsPreference,
                            final ElvesType elf) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreference = giftsPreference;
        this.elf = elf;
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

    public ElvesType getElf() {
        return elf;
    }
}
