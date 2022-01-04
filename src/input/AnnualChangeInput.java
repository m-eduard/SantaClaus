package input;

import java.util.List;

public class AnnualChangeInput {
    private final double newSantaBudget;
    private final List<GiftInput> newGifts;
    private final List<ChildInput> newChildren;
    private final List<ChildUpdateInput> childrenUpdates;

    public AnnualChangeInput(final double newSantaBudget, final List<GiftInput> newGifts,
                             final List<ChildInput> newChildren,
                             final List<ChildUpdateInput> childrenUpdates) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }
}
