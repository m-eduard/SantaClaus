package input;

import java.util.ArrayList;
import java.util.List;

public class InitialDataInput {
    /**
     * List of children
     */
    private final List<ChildInput> children;
    /**
     * Santa's gifts list
     */
    private final List<GiftInput> gifts;

    public InitialDataInput(final ArrayList<ChildInput> children,
                            final ArrayList<GiftInput> gifts) {
        this.children = children;
        this.gifts = gifts;
    }
}
