package entities;

import enums.Category;
import enums.ElvesType;

import java.util.List;

public final class ChildUpdate {
    private final int id;
    private final Double niceScore;
    private final List<Category> giftsPreferences;
    private final ElvesType elf;

    public ChildUpdate(final int id, final Double niceScore,
                       final List<Category> giftsPreference,
                       final ElvesType elf) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreference;
        this.elf = elf;
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

    public ElvesType getElf() {
        return elf;
    }
}
