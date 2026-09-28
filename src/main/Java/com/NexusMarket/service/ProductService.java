package com.NexusMarket.service;

import com.NexusMarket.model.Category;
import com.NexusMarket.model.Product;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.repository.CategoryRepository;
import com.NexusMarket.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product create(Product product, Long categoryId) {
        validate(product);
        product.setCategory(getCategory(categoryId));
        return productRepository.save(product);
    }

    public Product update(Long id, Product changes, Long categoryId) {
        Product product = getById(id);
        validate(changes);
        product.setName(changes.getName().trim());
        product.setDescription(changes.getDescription());
        product.setPrice(changes.getPrice());
        product.setStock(changes.getStock());
        product.setCategory(getCategory(categoryId));
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void delete(Long id) {
        productRepository.delete(getById(id));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + categoryId));
    }

    private void validate(Product product) {
        if (product == null || product.getName() == null || product.getName().isBlank()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El precio debe ser cero o positivo");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new BadRequestException("El inventario debe ser cero o positivo");
        }
        product.setName(product.getName().trim());
    }
}