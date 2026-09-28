package com.NexusMarket.buyermanagement;

import java.util.ArrayList;
import java.util.List;

public class Buyer {

    private String mainAddress;
    private List<String> additionalAddresses;
    private String commercialStatus;

    public Buyer(String mainAddress, String commercialStatus) {
        this.mainAddress = mainAddress;
        this.commercialStatus = commercialStatus;
        this.additionalAddresses = new ArrayList<>();
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public List<String> getAdditionalAddresses() {
        return additionalAddresses;
    }

    public void addAdditionalAddress(String address) {
        this.additionalAddresses.add(address);
    }

    public String getCommercialStatus() {
        return commercialStatus;
    }

    public void setCommercialStatus(String commercialStatus) {
        this.commercialStatus = commercialStatus;
    }
}