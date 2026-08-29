package com.comprasco.bakeprofit.service;

import com.comprasco.bakeprofit.entity.Category;
import com.comprasco.bakeprofit.exception.CategoryAlreadyExistsException;
import com.comprasco.bakeprofit.exception.CategoryNotFoundException;
import com.comprasco.bakeprofit.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /* CONSULTAS */

    public List<Category> findAll () {
        return categoryRepository.findAll();
    }

    public Category findById (Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria no encontrada con id: " + id));
    }

    public List<Category> searchByName (String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    /* ESCRITURA */

    @Transactional
    public Category create (String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new CategoryAlreadyExistsException(name);
        }

        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update (Long id, String name) {
        Category category = findById(id);

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new CategoryAlreadyExistsException(name);
        }

        category.setName(name);
        return categoryRepository.save(category);
    }

    /*
     * TODO: no se implementa delete() todavia.
     * Product tiene FK obligatoria (nullable = false) hacia Category, asi que
     * borrar una categoria con productos asociados rompe integridad referencial
     * en la base de datos. Resolverlo bien implica decidir si se bloquea el borrado
     * cuando existen productos asociados (lo que obliga a que CategoryService dependa
     * de ProductRepository, acoplando el modulo de Categoria con el de Producto) o si
     * se hace soft-delete en su lugar. No esta en el checklist de esta tarea, queda
     * pendiente de decisión con el equipo.
     */
}