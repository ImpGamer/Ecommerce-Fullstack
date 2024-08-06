package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.OrderService;
import com.backend.ecommerce.domain.model.Order;
import com.backend.ecommerce.domain.model.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
@CrossOrigin(origins = "http://localhost:3200")
public class OrderController {

    @Autowired private OrderService service;
    private final String SERVER_ERROR = "Error al realizar la orden. Intentelo mas tarde.";

    @PostMapping("/create")
    ResponseEntity<?> save(@RequestBody Order order,@RequestParam(name="fechaCreacion")String fechaCreacion,
                           @RequestParam(name="fechaEntrega")String fechaEntrega) {
        try {
            LocalDate fechaCreacionFormat = LocalDate.parse(fechaCreacion);
            LocalDate fechaEntregaFormat = LocalDate.parse(fechaEntrega);

            order.setFechaCreacion(fechaCreacionFormat);
            order.setFechaEntrega(fechaEntregaFormat);
            return service.guardar(order);
        }catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/updateStatus")
    ResponseEntity<?> updateStatusByID(@RequestParam(name = "id")Integer id,
                                      @RequestParam(name="status")OrderStatus status) {
        return service.changeStatus(id,status);
    }

    @GetMapping
    ResponseEntity<List<Order>> getAll() {
        return service.obtenerTodas();
    }

    @GetMapping("/get/{id}")
    ResponseEntity<Order> getByID(@PathVariable Integer id) {
        return service.buscarPor_ID(id);
    }

    @GetMapping("/getByUser")
    ResponseEntity<List<Order>> getByUser(@RequestParam("user_id")Integer user_id) {
        return service.buscarPor_Usuario(user_id);
    }
}
