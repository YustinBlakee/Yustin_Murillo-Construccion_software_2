package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.InvalidOrderStateException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.shippingmanagement.Shipment;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private final Map<String, Shipment> shipments = new ConcurrentHashMap<>();
    public Shipment create(String shipmentId,String orderId,String address){ if(shipmentId==null||shipmentId.isBlank()||orderId==null||orderId.isBlank()||address==null||address.isBlank()) throw new BadRequestException("Shipment data is required"); Shipment s=new Shipment(shipmentId,orderId,address); if(shipments.putIfAbsent(shipmentId,s)!=null) throw new ResourceConflictException("Shipment already exists: "+shipmentId); return s; }
    public Shipment getById(String id){ Shipment s=shipments.get(id); if(s==null) throw new ResourceNotFoundException("Shipment not found: "+id); return s; }
    public List<Shipment> getAll(){ return List.copyOf(shipments.values()); }
    public Shipment dispatch(String id){ Shipment s=getById(id); if("DELIVERED".equals(s.getStatus())) throw new InvalidOrderStateException("Delivered shipment cannot be dispatched"); s.setStatus("IN_TRANSIT"); return s; }
    public Shipment deliver(String id){ Shipment s=getById(id); if(!"IN_TRANSIT".equals(s.getStatus())) throw new InvalidOrderStateException("Shipment must be in transit before delivery"); s.setStatus("DELIVERED"); return s; }
}
