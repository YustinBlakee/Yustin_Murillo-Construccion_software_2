package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.refundmanagement.Refund;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RefundService {
    private final Map<String, Refund> refunds = new ConcurrentHashMap<>();
    public Refund request(String refundId,String orderId,BigDecimal amount){ if(refundId==null||refundId.isBlank()||orderId==null||orderId.isBlank()||amount==null||amount.signum()<=0) throw new BadRequestException("Refund data is invalid"); Refund r=new Refund(refundId,orderId,amount); if(refunds.putIfAbsent(refundId,r)!=null) throw new ResourceConflictException("Refund already exists: "+refundId); return r; }
    public Refund getById(String id){ Refund r=refunds.get(id); if(r==null) throw new ResourceNotFoundException("Refund not found: "+id); return r; }
    public List<Refund> getAll(){ return List.copyOf(refunds.values()); }
    public Refund approve(String id){ Refund r=getById(id); r.setStatus("APPROVED"); return r; }
    public Refund complete(String id){ Refund r=getById(id); r.setStatus("COMPLETED"); return r; }
}
