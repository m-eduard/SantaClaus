package entities;

import enums.Category;

public class Gift {
    private final String productName;
    private final double price;
    private final Category category;

    public Gift(final String productName, final double price,
                     final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }
}
