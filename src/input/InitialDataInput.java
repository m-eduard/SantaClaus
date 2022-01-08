package input;

import java.util.List;

/**
 * Class that gathers the initial set of gifts and children, parsed from the input
 */
public final class InitialDataInput {
    /**
     * Santa's list of children
     */
    private final List<ChildInput> children;
    /**
     * Santa's gifts list
     */
    private final List<GiftInput> gifts;

    public InitialDataInput(final List<ChildInput> children,
                            final List<GiftInput> gifts) {
        this.children = children;
        this.gifts = gifts;
    }

    public List<ChildInput> getChildren() {
        return children;
    }

    public List<GiftInput> getGifts() {
        return gifts;
    }
}
