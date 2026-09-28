package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.returnmanagement.ReturnRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ReturnService {
    private final Map<String, ReturnRequest> returns = new ConcurrentHashMap<>();
    public ReturnRequest request(String returnId,String orderId,String reason){ if(returnId==null||returnId.isBlank()||orderId==null||orderId.isBlank()||reason==null||reason.isBlank()) throw new BadRequestException("Return data is required"); ReturnRequest r=new ReturnRequest(returnId,orderId,reason); if(returns.putIfAbsent(returnId,r)!=null) throw new ResourceConflictException("Return already exists: "+returnId); return r; }
    public ReturnRequest getById(String id){ ReturnRequest r=returns.get(id); if(r==null) throw new ResourceNotFoundException("Return not found: "+id); return r; }
    public List<ReturnRequest> getAll(){ return List.copyOf(returns.values()); }
    public ReturnRequest approve(String id){ ReturnRequest r=getById(id); r.setStatus("APPROVED"); return r; }
    public ReturnRequest reject(String id){ ReturnRequest r=getById(id); r.setStatus("REJECTED"); return r; }
}
