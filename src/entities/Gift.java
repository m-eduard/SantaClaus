package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Category;

public final class Gift {
    private final String productName;
    private final double price;
    private final Category category;
    @JsonIgnore
    private int quantity;

    public Gift(final String productName, final double price,
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
