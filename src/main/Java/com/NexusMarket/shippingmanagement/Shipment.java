package com.NexusMarket.shippingmanagement;

public class Shipment {
    private final String shipmentId;
    private final String orderId;
    private final String address;
    private String status;

    public Shipment(String shipmentId, String orderId, String address) {
        this.shipmentId = shipmentId; this.orderId = orderId; this.address = address; this.status = "PREPARING";
    }
    public String getShipmentId() { return shipmentId; }
    public String getOrderId() { return orderId; }
    public String getAddress() { return address; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
