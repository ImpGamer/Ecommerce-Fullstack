package com.backend.ecommerce.application.service;

import com.backend.ecommerce.domain.model.Category;
import com.backend.ecommerce.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category guardar(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> obtener_todas() {
        return categoryRepository.findAll();
    }

    public Category buscarCategoria_ID(Integer id)throws ClassNotFoundException {
        Optional<Category> categoryID = categoryRepository.findById(id);
        if(categoryID.isEmpty()) {
            throw new ClassNotFoundException("Categoria no encontrada");
        }
        return categoryID.get();
    }
    public String eliminar(Integer id) {
        try {
            Category categoriaBD = buscarCategoria_ID(id);
            categoryRepository.delete(categoriaBD);
            return "Categoria eliminada correctamente";
        }catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return "Categoria con ID: "+id+" no existe";
        }
    }
}
