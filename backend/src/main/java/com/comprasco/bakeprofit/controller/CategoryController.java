package com.comprasco.bakeprofit.controller;

import com.comprasco.bakeprofit.dto.CategoryRequest;
import com.comprasco.bakeprofit.entity.Category;
import com.comprasco.bakeprofit.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        categoryService.update(id, request.name());
        return ResponseEntity.noContent().build();
    }
}