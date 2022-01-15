package input;

import enums.Category;

/**
 * Information about a gift, parsed from the input
 */
public final class GiftInput {
    private final String productName;
    private final double price;
    private final Category category;
    private final int quantity;

    public GiftInput(final String productName, final double price,
                     final Category category, final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
}
