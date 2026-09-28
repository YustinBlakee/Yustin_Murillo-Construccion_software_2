package com.NexusMarket.refundmanagement;

import java.math.BigDecimal;

public class Refund {
    private final String refundId;
    private final String orderId;
    private final BigDecimal amount;
    private String status;

    public Refund(String refundId, String orderId, BigDecimal amount) {
        this.refundId = refundId; this.orderId = orderId; this.amount = amount; this.status = "REQUESTED";
    }
    public String getRefundId() { return refundId; }
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
