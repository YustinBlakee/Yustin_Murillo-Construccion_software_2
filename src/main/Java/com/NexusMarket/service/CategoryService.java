package com.NexusMarket.service;

import com.NexusMarket.model.Category;
import com.NexusMarket.repository.CategoryRepository;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        if (category == null) {
            throw new BadRequestException("La categoría es obligatoria");
        }
        validateName(category.getName());
        if (categoryRepository.existsByNameIgnoreCase(category.getName().trim())) {
            throw new ResourceConflictException("Ya existe una categoría con ese nombre");
        }
        category.setName(category.getName().trim());
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category changes) {
        Category category = getById(id);
        if (changes == null) {
            throw new BadRequestException("La categoría es obligatoria");
        }
        validateName(changes.getName());
        String name = changes.getName().trim();
        if (!category.getName().equalsIgnoreCase(name)
                && categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ResourceConflictException("Ya existe una categoría con ese nombre");
        }
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("El nombre de la categoría es obligatorio");
        }
    }
}