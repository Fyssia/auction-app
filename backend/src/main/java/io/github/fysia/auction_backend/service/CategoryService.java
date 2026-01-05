package io.github.fysia.auction_backend.service;


import io.github.fysia.auction_backend.dto.CategoryDto;
import io.github.fysia.auction_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAllByOrderByNameAsc()
            .stream()
            .map(c -> new CategoryDto(c.getId(), c.getName()))
            .toList();
    }
}
