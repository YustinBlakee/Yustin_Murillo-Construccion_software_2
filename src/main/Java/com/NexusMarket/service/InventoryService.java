package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.InventoryUnavailableException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.inventorymanagement.Inventory;
import com.NexusMarket.inventorymanagement.InventoryMovement;
import com.NexusMarket.inventorymanagement.InventoryMovementType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final Map<String, Inventory> inventories = new ConcurrentHashMap<>();
    private final Map<String, List<InventoryMovement>> movements = new ConcurrentHashMap<>();

    public Inventory create(Inventory inventory){ if(inventory==null||inventory.getInventoryId()==null||inventory.getProductId()==null||inventory.getWarehouseId()==null) throw new BadRequestException("Inventory identifiers are required"); if(inventories.putIfAbsent(inventory.getInventoryId(),inventory)!=null) throw new ResourceConflictException("Inventory already exists: "+inventory.getInventoryId()); return inventory; }
    public Inventory getById(String id){ Inventory i=inventories.get(id); if(i==null) throw new ResourceNotFoundException("Inventory not found: "+id); return i; }
    public List<Inventory> getAll(){ return List.copyOf(inventories.values()); }
    public Inventory addStock(String id,int amount){ if(amount<=0) throw new BadRequestException("Amount must be greater than zero"); Inventory i=getById(id); i.addStock(amount); record(id,InventoryMovementType.ENTRY,amount); return i; }
    public Inventory reserve(String id,int amount){ if(amount<=0) throw new BadRequestException("Amount must be greater than zero"); Inventory i=getById(id); if(i.getQuantity()<amount) throw new InventoryUnavailableException("Insufficient inventory for reservation"); i.removeStock(amount); record(id,InventoryMovementType.RESERVATION,amount); return i; }
    public Inventory sell(String id,int amount){ if(amount<=0) throw new BadRequestException("Amount must be greater than zero"); Inventory i=getById(id); if(i.getQuantity()<amount) throw new InventoryUnavailableException("Insufficient inventory for sale"); i.removeStock(amount); record(id,InventoryMovementType.SALE,amount); return i; }
    public Inventory returnStock(String id,int amount){ if(amount<=0) throw new BadRequestException("Amount must be greater than zero"); Inventory i=getById(id); i.addStock(amount); record(id,InventoryMovementType.RETURN,amount); return i; }
    public List<InventoryMovement> getMovements(String id){ getById(id); return List.copyOf(movements.getOrDefault(id,List.of())); }
    private void record(String id, InventoryMovementType type, int quantity){ movements.computeIfAbsent(id,k->new java.util.concurrent.CopyOnWriteArrayList<>()).add(new InventoryMovement(id+"-"+(movements.getOrDefault(id,List.of()).size()+1),type,quantity)); }
}
