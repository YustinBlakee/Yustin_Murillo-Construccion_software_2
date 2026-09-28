package com.NexusMarket.service;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final MarketUserService userService;
    private final SellerService sellerService;
    private final BuyerService buyerService;
    private final WarehouseService warehouseService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final PurchaseOrderService orderService;

    public ReportService(MarketUserService userService, SellerService sellerService, BuyerService buyerService,
                         WarehouseService warehouseService, InventoryService inventoryService,
                         ProductService productService, PurchaseOrderService orderService) {
        this.userService=userService; this.sellerService=sellerService; this.buyerService=buyerService;
        this.warehouseService=warehouseService; this.inventoryService=inventoryService;
        this.productService=productService; this.orderService=orderService;
    }

    public Map<String,Object> summary(){
        Map<String,Object> report=new LinkedHashMap<>();
        report.put("users", userService.getAll().size());
        report.put("buyers", buyerService.getAll().size());
        report.put("sellers", sellerService.getAll().size());
        report.put("warehouses", warehouseService.getAll().size());
        report.put("inventoryRecords", inventoryService.getAll().size());
        report.put("products", productService.getAll().size());
        report.put("orders", orderService.getAll().size());
        return report;
    }
}
