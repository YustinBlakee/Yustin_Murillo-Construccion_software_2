package com.nexusmarket.catalogmanagement;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String productId;
    private String name;
    private ProductType type;
    private List<String> variants;
    private ProductStatus status;

    public Product(
            String productId,
            String name,
            ProductType type,
            ProductStatus status) {

        this.productId = productId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.variants = new ArrayList<>();
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

    public List<String> getVariants() {
        return variants;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void addVariant(String variant) {
        this.variants.add(variant);
    }
}