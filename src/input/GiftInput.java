package input;

import enums.Category;

public final class GiftInput {
    private final String productName;
    private final double price;
    private final Category category;

    public GiftInput(final String productName, final double price,
                     final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }
}
