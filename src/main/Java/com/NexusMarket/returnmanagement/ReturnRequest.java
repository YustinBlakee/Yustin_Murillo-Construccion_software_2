package com.NexusMarket.returnmanagement;

public class ReturnRequest {
    private final String returnId;
    private final String orderId;
    private final String reason;
    private String status;

    public ReturnRequest(String returnId, String orderId, String reason) {
        this.returnId = returnId; this.orderId = orderId; this.reason = reason; this.status = "REQUESTED";
    }
    public String getReturnId() { return returnId; }
    public String getOrderId() { return orderId; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
