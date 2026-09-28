package com.NexusMarket.service;

import com.NexusMarket.billingmanagement.Invoice;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
    private final Map<String, Invoice> invoices = new ConcurrentHashMap<>();
    public Invoice issue(String invoiceId,String orderId,BigDecimal amount){ if(invoiceId==null||invoiceId.isBlank()||orderId==null||orderId.isBlank()||amount==null||amount.signum()<0) throw new BadRequestException("Invoice data is invalid"); Invoice i=new Invoice(invoiceId,orderId,amount); if(invoices.putIfAbsent(invoiceId,i)!=null) throw new ResourceConflictException("Invoice already exists: "+invoiceId); return i; }
    public Invoice getById(String id){ Invoice i=invoices.get(id); if(i==null) throw new ResourceNotFoundException("Invoice not found: "+id); return i; }
    public List<Invoice> getAll(){ return List.copyOf(invoices.values()); }
    public Invoice markPaid(String id){ Invoice i=getById(id); i.setStatus("PAID"); return i; }
    public Invoice cancel(String id){ Invoice i=getById(id); i.setStatus("CANCELLED"); return i; }
}
