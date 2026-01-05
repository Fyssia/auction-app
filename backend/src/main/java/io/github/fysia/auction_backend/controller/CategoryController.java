package io.github.fysia.auction_backend.controller;

import io.github.fysia.auction_backend.dto.CategoryDto;
import io.github.fysia.auction_backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.listCategories();
    }
}
