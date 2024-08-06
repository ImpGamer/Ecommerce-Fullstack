package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.CategoryService;
import com.backend.ecommerce.domain.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@CrossOrigin(origins = "http://localhost:3200")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @PostMapping("/create")
    ResponseEntity<Category> save(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.guardar(category), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<Iterable<Category>> getAll() {
        return ResponseEntity.ok(categoryService.obtener_todas());
    }
    @GetMapping("/{id}")
    ResponseEntity<Category> findById(@PathVariable Integer id)throws ClassNotFoundException {
        return ResponseEntity.ok(categoryService.buscarCategoria_ID(id));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.eliminar(id));
    }
}
