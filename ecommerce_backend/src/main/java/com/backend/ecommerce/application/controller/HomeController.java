package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.ProductService;
import com.backend.ecommerce.domain.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3200")
@Slf4j
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping
    ResponseEntity<Iterable<Product>> mostrar_todos() {
        return ResponseEntity.ok(productService.obtener_todos());
    }

    @GetMapping("/find")
    ResponseEntity<Product> encontrar_porID(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(productService.buscar_productoID(id));
        }catch (ClassNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
    }
}
