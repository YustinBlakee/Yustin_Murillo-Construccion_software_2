package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.warehousemanagement.Warehouse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    private final Map<String, Warehouse> warehouses = new ConcurrentHashMap<>();
    public Warehouse create(Warehouse warehouse){ validate(warehouse); if(warehouses.putIfAbsent(warehouse.getWarehouseId(),warehouse)!=null) throw new ResourceConflictException("Warehouse already exists: "+warehouse.getWarehouseId()); return warehouse; }
    public Warehouse getById(String id){ Warehouse w=warehouses.get(id); if(w==null) throw new ResourceNotFoundException("Warehouse not found: "+id); return w; }
    public List<Warehouse> getAll(){ return List.copyOf(warehouses.values()); }
    public Warehouse update(String id, Warehouse changes){ validate(changes); Warehouse w=getById(id); w.setName(changes.getName()); w.setType(changes.getType()); return w; }
    public void delete(String id){ getById(id); warehouses.remove(id); }
    private void validate(Warehouse w){ if(w==null||w.getWarehouseId()==null||w.getWarehouseId().isBlank()||w.getName()==null||w.getName().isBlank()||w.getType()==null) throw new BadRequestException("Warehouse id, name and type are required"); }
}
