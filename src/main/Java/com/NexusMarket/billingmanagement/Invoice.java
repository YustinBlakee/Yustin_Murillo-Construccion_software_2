package com.NexusMarket.billingmanagement;

import java.math.BigDecimal;

public class Invoice {
    private final String invoiceId;
    private final String orderId;
    private final BigDecimal amount;
    private String status;

    public Invoice(String invoiceId, String orderId, BigDecimal amount) {
        this.invoiceId = invoiceId; this.orderId = orderId; this.amount = amount; this.status = "ISSUED";
    }
    public String getInvoiceId() { return invoiceId; }
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
