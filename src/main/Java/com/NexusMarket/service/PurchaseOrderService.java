package com.NexusMarket.service;

import com.NexusMarket.dto.OrderItemRequest;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.model.MarketUser;
import com.NexusMarket.model.OrderItem;
import com.NexusMarket.model.Product;
import com.NexusMarket.model.PurchaseOrder;
import com.NexusMarket.repository.MarketUserRepository;
import com.NexusMarket.repository.ProductRepository;
import com.NexusMarket.repository.PurchaseOrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;
    private final MarketUserRepository userRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository orderRepository,
            MarketUserRepository userRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public PurchaseOrder create(Long userId, List<OrderItemRequest> requestedItems) {
        if (requestedItems == null || requestedItems.isEmpty()) {
            throw new BadRequestException("El pedido debe incluir al menos un producto");
        }
        MarketUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userId));
        PurchaseOrder order = new PurchaseOrder();
        order.setUser(user);
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest requestedItem : requestedItems) {
            if (requestedItem == null || requestedItem.productId() == null
                    || requestedItem.quantity() == null || requestedItem.quantity() <= 0) {
                throw new BadRequestException("Cada producto debe tener un identificador y cantidad positiva");
            }
            Product product = productRepository.findById(requestedItem.productId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado: " + requestedItem.productId()));
            if (product.getStock() < requestedItem.quantity()) {
                throw new ResourceConflictException("Inventario insuficiente para: " + product.getName());
            }

            product.setStock(product.getStock() - requestedItem.quantity());
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(requestedItem.quantity());
            item.setUnitPrice(product.getPrice());
            order.addItem(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(requestedItem.quantity())));
        }

        order.setTotal(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public PurchaseOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> getAll() {
        return orderRepository.findAll();
    }
}