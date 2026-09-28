package com.NexusMarket.ordermanagement;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final String cartId;
    private final String buyerId;
    private final List<OrderItem> items = new ArrayList<>();

    public Cart(String cartId, String buyerId) {
        this.cartId = cartId;
        this.buyerId = buyerId;
    }

    public String getCartId() { return cartId; }
    public String getBuyerId() { return buyerId; }
    public List<OrderItem> getItems() { return items; }

    public void addItem(String productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        items.stream().filter(i -> i.getProductId().equals(productId)).findFirst()
                .ifPresentOrElse(i -> i.increaseQuantity(quantity),
                        () -> items.add(new OrderItem(productId, quantity)));
    }

    public void removeItem(String productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() { items.clear(); }
}
