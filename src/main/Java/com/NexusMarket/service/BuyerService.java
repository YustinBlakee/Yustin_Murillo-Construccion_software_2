package com.NexusMarket.service;

import com.NexusMarket.buyermanagement.Buyer;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {
    private final Map<String, Buyer> buyers = new ConcurrentHashMap<>();

    public Buyer register(String buyerId, Buyer buyer) {
        validateId(buyerId); validate(buyer);
        if (buyers.putIfAbsent(buyerId, buyer) != null) throw new ResourceConflictException("Buyer already exists: " + buyerId);
        return buyer;
    }
    public Buyer getById(String buyerId) { Buyer b = buyers.get(buyerId); if (b == null) throw new ResourceNotFoundException("Buyer not found: " + buyerId); return b; }
    public List<Buyer> getAll() { return List.copyOf(buyers.values()); }
    public Buyer update(String buyerId, Buyer changes) { validate(changes); Buyer b=getById(buyerId); b.setMainAddress(changes.getMainAddress()); b.setCommercialStatus(changes.getCommercialStatus()); return b; }
    public void delete(String buyerId) { buyers.remove(buyerId); }
    public void addAddress(String buyerId, String address) { if(address==null||address.isBlank()) throw new BadRequestException("Address is required"); getById(buyerId).addAdditionalAddress(address.trim()); }
    private void validateId(String id){ if(id==null||id.isBlank()) throw new BadRequestException("Buyer id is required"); }
    private void validate(Buyer b){ if(b==null||b.getMainAddress()==null||b.getMainAddress().isBlank()) throw new BadRequestException("Main address is required"); if(b.getCommercialStatus()==null||b.getCommercialStatus().isBlank()) throw new BadRequestException("Commercial status is required"); }
}
