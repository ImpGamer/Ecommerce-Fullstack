package com.backend.ecommerce.service;

import com.backend.ecommerce.application.service.ProductService;
import com.backend.ecommerce.domain.model.Product;
import com.backend.ecommerce.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductoRepository repository;
    @InjectMocks
    private ProductService service;
    private Product producto;

    @BeforeEach
    void setProduct() {
        producto = new Product(4,"Aspiradora","00001","Aspiradora de 30cm","http://localhost:8085/images/aspiradora.jpg",
                300.4f, LocalDateTime.now(),LocalDateTime.now(),new ArrayList<>());
    }
    @Test
    void testGetImageFrom_URL_Image() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(this.producto));

        ResponseEntity<String> response = service.eliminar(2);
        assertAll(() -> assertEquals("No se pudo eliminar la imagen "+producto.getUrl_image(),response.getBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode()));

    }
}
