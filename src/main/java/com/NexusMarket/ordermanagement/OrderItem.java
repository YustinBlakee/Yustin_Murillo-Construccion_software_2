package main.java.com.NexusMarket.ordermanagement;



public class OrderItem {

    private String productId;
    private int quantity;

    public OrderItem(String productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Order item quantity must be greater than zero."
            );
        }

        this.productId = productId;
        this.quantity = quantity;
    }
 
    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        quantity += amount;
    }

    public void decreaseQuantity(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        if (amount > quantity) {
            throw new IllegalArgumentException(
                    "Cannot decrease quantity below zero."
            );
        }

        quantity -= amount;
    }
}