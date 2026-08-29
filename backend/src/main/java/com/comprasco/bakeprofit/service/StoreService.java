package com.comprasco.bakeprofit.service;

import com.comprasco.bakeprofit.entity.Store;
import com.comprasco.bakeprofit.exception.StoreAlreadyExistsException;
import com.comprasco.bakeprofit.exception.StoreNotFoundException;
import com.comprasco.bakeprofit.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /* CONSULTAS */

    public List<Store> findAll () {
        return storeRepository.findAll();
    }

    public Store findById (Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("Tienda no encontrada con id: " + id));
    }

    public List<Store> search(String name, String city) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCity = city != null && !city.isBlank();

        if (hasName && hasCity) {
            return storeRepository.findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(name, city);
        } else if (hasName) {
            return storeRepository.findByNameContainingIgnoreCase(name);
        } else if (hasCity) {
            return storeRepository.findByCityContainingIgnoreCase(city);
        }

        return storeRepository.findAll();
    }

    public List<Store> findActive () {
        return storeRepository.findByActiveTrue();
    }

    public List<Store> findInactive () {
        return storeRepository.findByActiveFalse();
    }

    /* ESCRITURA */

    @Transactional
    public Store create (String name, String city) {
        if (storeRepository.existsByNameIgnoreCase(name)) {
            throw new StoreAlreadyExistsException(name);
        }

        Store store = new Store();
        store.setName(name);
        store.setCity(city);

        return storeRepository.save(store);
    }

    @Transactional
    public Store update(Long id, String name, String city) {
        Store store = findById(id);

        if (storeRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new StoreAlreadyExistsException(name);
        }

        store.setName(name);
        store.setCity(city);

        return storeRepository.save(store);
    }

    @Transactional
    public void deactivateStore (Long id) {
        Store store = findById(id);
        store.setActive(false);
    }

    @Transactional
    public void activateStore (Long id) {
        Store store = findById(id);
        store.setActive(true);
    }
}