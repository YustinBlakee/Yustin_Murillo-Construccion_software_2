package com.nexusmarket.inventorymanagement;

public class InventoryMovement {

    private String movementId;
    private InventoryMovementType type;
    private int quantity;

    public InventoryMovement(
            String movementId,
            InventoryMovementType type,
            int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Movement quantity must be greater than zero."
            );
        }

        this.movementId = movementId;
        this.type = type;
        this.quantity = quantity;
    }

    public String getMovementId() {
        return movementId;
    }

    public InventoryMovementType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}