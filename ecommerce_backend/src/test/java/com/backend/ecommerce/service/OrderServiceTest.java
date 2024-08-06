package com.backend.ecommerce.service;

import com.backend.ecommerce.application.service.OrderService;
import com.backend.ecommerce.domain.model.Order;
import com.backend.ecommerce.domain.model.OrderProduct;
import com.backend.ecommerce.domain.model.Product;
import com.backend.ecommerce.domain.model.User;
import com.backend.ecommerce.domain.model.enums.OrderStatus;
import com.backend.ecommerce.domain.model.enums.UserType;
import com.backend.ecommerce.domain.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;
    @InjectMocks
    private OrderService service;
    private Order orden;

    @Test
    void test_save_an_order() {
        when(repository.save(ArgumentMatchers.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        User usuario = new User(1,"Antonio","Martinez","Martinez","A_martinez@gmail.com","cerca de mi casa"
        ,"55323901","my_passwordPRO", UserType.USER,LocalDateTime.now(),LocalDateTime.MAX);
        Product[] productos = {
                new Product(1,"Televisor","90912","Televisor de 42 pulgadas LG","http://imagen-pro.jpg",
                        200f,LocalDateTime.now(),LocalDateTime.now(),new ArrayList<>()),
                new Product(2,"Tarjeta Madre","90913","Tarjeta Madre para PC","http://imagen-pro.jpg",
                        150.3f,LocalDateTime.now(),LocalDateTime.now(),new ArrayList<>()),
                new Product(3,"Balon","90914","Balon de Futbol","http://imagen-pro.jpg",
                        70.90f,LocalDateTime.now(),LocalDateTime.now(),new ArrayList<>())
        };
        List<OrderProduct> productosCompra = List.of(
                new OrderProduct(1,3,0,productos[0],orden),
                new OrderProduct(2,1,0,productos[1],orden),
                new OrderProduct(3,2,0,productos[2],orden)
        );
        orden = new Order(1, LocalDate.now(),LocalDate.of(2024,10,12),usuario,OrderStatus.CONFIRMED,
                productosCompra);

        try {
            ResponseEntity<?> response = service.guardar(orden);

            assertAll(
                    () -> assertEquals(Order.class,response.getBody()),
                    () -> assertNotNull(response, "La respuesta no debe ser nula"),
                    () -> assertEquals(HttpStatus.CREATED, response.getStatusCode(), "El código de estado debe ser 200"),
                    () -> assertNotEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "El código de estado no debe ser 400")
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Se lanzó una excepción: " + ex.getMessage());
        }
    }
}
