package com.comprasco.bakeprofit.repository;

import com.comprasco.bakeprofit.entity.Product;
import com.comprasco.bakeprofit.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase (String name);

    List<Product> findByCategory (Category category);
}