package input;

import java.util.List;

/**
 * New data added after a year passed, retrieved from parsing the input files
 */
public final class AnnualChangeInput {
    private final double newSantaBudget;
    private final List<GiftInput> newGifts;
    private final List<ChildInput> newChildren;
    private final List<ChildUpdateInput> childrenUpdates;

    public AnnualChangeInput(final double newSantaBudget,
                             final List<GiftInput> newGifts,
                             final List<ChildInput> newChildren,
                             final List<ChildUpdateInput> childrenUpdates) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }

    public double getNewSantaBudget() {
        return newSantaBudget;
    }

    public List<GiftInput> getNewGifts() {
        return newGifts;
    }

    public List<ChildInput> getNewChildren() {
        return newChildren;
    }

    public List<ChildUpdateInput> getChildrenUpdates() {
        return childrenUpdates;
    }
}
