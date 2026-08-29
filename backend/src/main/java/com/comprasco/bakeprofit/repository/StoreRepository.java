package com.comprasco.bakeprofit.repository;

import com.comprasco.bakeprofit.entity.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByNameContainingIgnoreCase (String name);

    List<Store> findByCityContainingIgnoreCase (String city);

    List<Store> findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(String name, String city);

    List<Store> findByActiveTrue ();

    List<Store> findByActiveFalse ();

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}