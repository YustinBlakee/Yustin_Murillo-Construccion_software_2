package com.NexusMarket.sellermanagement;

public class Seller {

    private String sellerId;
    private String businessName;
    private String status;

    public Seller(String sellerId, String businessName, String status) {
        this.sellerId = sellerId;
        this.businessName = businessName;
        this.status = status;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getStatus() {
        return status;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}