package com.backend.ecommerce.application.service;

import com.backend.ecommerce.domain.model.Product;
import com.backend.ecommerce.domain.repositories.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductoRepository productoRepository;
    @Value("${ruta.imagenes}")
    private String rutaImagenes;
    private final String URL_IMAGES = "http://localhost:8085/images";

    public ResponseEntity<Product> guardar(Product product,MultipartFile image) {
        if(image != null && !Objects.requireNonNull(image.getOriginalFilename()).equals("empty.txt")) {
            saveImage(image);
            return ResponseEntity.ok(productoRepository.save(product));
        }
        product.setUrl_image(URL_IMAGES+"/default.jpg");
        return new ResponseEntity<>(productoRepository.save(product),HttpStatus.OK);

    }
    public ResponseEntity<Product> actualizar(Integer id,Product product,MultipartFile image) {
        Optional<Product> productBD = productoRepository.findById(id);
        if(productBD.isPresent()) {
            Product productSave = productBD.get();
            try {
                File imagen = new File(productSave.getUrl_image());
                String path_image = rutaImagenes+imagen.getName();

                if(!path_image.equals(rutaImagenes+"default.jpg")) {
                    Path oldUrl_image = Paths.get(path_image);
                    Files.delete(oldUrl_image);
                }

                if(image != null && !product.getUrl_image().equals(URL_IMAGES+"/empty.txt")) {
                    saveImage(image);
                } else {
                    product.setUrl_image(URL_IMAGES +"/default.jpg");
                }

                return ResponseEntity.ok(productoRepository.save(product));
            }catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return new ResponseEntity<>(new Product(),HttpStatus.NOT_FOUND);
    }
    public List<Product> obtener_todos() {
        return productoRepository.findAll();
    }

    public Product buscar_productoID(Integer id)throws ClassNotFoundException {
        Optional<Product> productID = productoRepository.findById(id);
        if(productID.isEmpty()) {
            throw new ClassNotFoundException("Producto con ID: "+id+" no encontrado");
        }
        return productID.get();
    }
    public ResponseEntity<String> eliminar(Integer id) {
        Optional<Product> productBD = productoRepository.findById(id);
        if(productBD.isPresent()) {
            String url_image = productBD.get().getUrl_image();
            File imagen = new File(url_image);
            String path_image = rutaImagenes+imagen.getName();

            if(!path_image.equals(rutaImagenes+"default.jpg")) {
                try {
                    Files.delete(Paths.get(rutaImagenes+ imagen.getName()));
                }catch (IOException ex) {
                    log.error(ex.getMessage());
                    return new ResponseEntity<>("No se pudo eliminar la imagen "+url_image,HttpStatus.NOT_FOUND);
                }
            }

            productoRepository.delete(productBD.get());
            return ResponseEntity.ok("Se elimino correctamente el producto");
        }

        return new ResponseEntity<>("No se encontro el producto con ID: "+id,HttpStatus.BAD_REQUEST);
    }

    private void saveImage(MultipartFile image) {
        try {
            String imageName = image.getOriginalFilename();
            byte[] imageBytes = image.getBytes();

            Path imageSave = Paths.get(rutaImagenes+imageName);
            Files.write(imageSave,imageBytes);
        }catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
