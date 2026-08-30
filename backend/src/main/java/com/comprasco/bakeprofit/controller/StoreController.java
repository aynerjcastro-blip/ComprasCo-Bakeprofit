package com.comprasco.bakeprofit.controller;

import com.comprasco.bakeprofit.dto.StoreRequest;
import com.comprasco.bakeprofit.entity.Store;
import com.comprasco.bakeprofit.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<Store>> findAll() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> findById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Store>> search(@RequestParam String name) {
        return ResponseEntity.ok(storeService.searchByName(name));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Store>> findActive() {
        return ResponseEntity.ok(storeService.findActive());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Store>> findInactive() {
        return ResponseEntity.ok(storeService.findInactive());
    }

    @PostMapping
    public ResponseEntity<Store> create(@Valid @RequestBody StoreRequest request) {
        return ResponseEntity.ok(storeService.create(request.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody StoreRequest request) {
        storeService.update(id, request.name());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        storeService.deactivateStore(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        storeService.activateStore(id);
        return ResponseEntity.noContent().build();
    }
}