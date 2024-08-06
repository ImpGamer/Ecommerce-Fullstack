package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.ProductService;
import com.backend.ecommerce.domain.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/products")
@Slf4j
@CrossOrigin(origins = "http://localhost:3200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value ="/add",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<Product> guardar(@RequestPart("product") Product product, @RequestPart(value ="image",required = false)MultipartFile image) {
        log.info("Producto '{}' almacenado en la DB",product.getName());
        return productService.guardar(product,image);
    }

    @GetMapping
    ResponseEntity<Iterable<Product>> mostrar_todos() {
        return ResponseEntity.ok(productService.obtener_todos());
    }

    @GetMapping("/find")
    ResponseEntity<Product> encontrar_porID(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(productService.buscar_productoID(id));
        }catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new Product(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    ResponseEntity<String> eliminar(@RequestParam Integer id) {
        return productService.eliminar(id);
    }
    @PutMapping(value = "update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<Product> acutalizar(@PathVariable Integer id,@RequestPart("product") Product product,
                                        @RequestPart(value ="image",required = false)MultipartFile image) {
        return productService.actualizar(id,product,image);
    }
}
