package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.sellermanagement.Seller;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    private final Map<String, Seller> sellers = new ConcurrentHashMap<>();

    public Seller registerByAdministrator(Seller seller) {
        validate(seller);
        if (sellers.putIfAbsent(seller.getSellerId(), seller) != null) throw new ResourceConflictException("Seller already exists: " + seller.getSellerId());
        return seller;
    }
    public Seller getById(String id) { Seller s=sellers.get(id); if(s==null) throw new ResourceNotFoundException("Seller not found: "+id); return s; }
    public List<Seller> getAll(){ return List.copyOf(sellers.values()); }
    public Seller update(String id, Seller changes){ validate(changes); Seller s=getById(id); s.setBusinessName(changes.getBusinessName()); s.setStatus(changes.getStatus()); return s; }
    public void delete(String id){ getById(id); sellers.remove(id); }
    private void validate(Seller s){ if(s==null||s.getSellerId()==null||s.getSellerId().isBlank()||s.getBusinessName()==null||s.getBusinessName().isBlank()||s.getStatus()==null||s.getStatus().isBlank()) throw new BadRequestException("Seller id, business name and status are required"); }
}
