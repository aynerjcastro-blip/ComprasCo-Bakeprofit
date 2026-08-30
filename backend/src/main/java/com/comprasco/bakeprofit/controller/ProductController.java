package com.comprasco.bakeprofit.controller;

import com.comprasco.bakeprofit.dto.ProductResponse;
import com.comprasco.bakeprofit.dto.ProductRequest;
import com.comprasco.bakeprofit.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByIdResponse(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> findActive() {
        return ResponseEntity.ok(productService.findActive());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<ProductResponse>> findInactive() {
        return ResponseEntity.ok(productService.findInactive());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> search(@RequestParam(required = false) String name, 
                                                        @RequestParam(required = false) Long idCategory) {
        return ResponseEntity.ok(productService.search(name, idCategory));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create (@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.create(request.name(), request.unit(), request.idCategory()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update (@PathVariable Long id, 
                                                   @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request.name(), 
                                                request.unit(), request.idCategory()));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate (@PathVariable Long id) {
        productService.activateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate (@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }
}