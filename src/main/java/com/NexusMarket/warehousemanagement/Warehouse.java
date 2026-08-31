package main.java.com.NexusMarket.warehousemanagement;

public class Warehouse {

    private String warehouseId;
    private String name;
    private WarehouseType type;

    public Warehouse(String warehouseId, String name, WarehouseType type) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.type = type;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getName() {
        return name;
    }

    public WarehouseType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(WarehouseType type) {
        this.type = type;
    }
}