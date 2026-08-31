package com.nexusmarket.inventorymanagement;

public class Inventory {

    private String inventoryId;
    private String productId;
    private String warehouseId;
    private int quantity;

    public Inventory(
            String inventoryId,
            String productId,
            String warehouseId,
            int quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Inventory quantity cannot be negative."
            );
        }

        this.inventoryId = inventoryId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public String getProductId() {
        return productId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addStock(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "The amount to add must be greater than zero."
            );
        }

        quantity += amount;
    }

    public void removeStock(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "The amount to remove must be greater than zero."
            );
        }

        if (amount > quantity) {
            throw new IllegalArgumentException(
                    "Insufficient inventory."
            );
        }

        quantity -= amount;
    }
}