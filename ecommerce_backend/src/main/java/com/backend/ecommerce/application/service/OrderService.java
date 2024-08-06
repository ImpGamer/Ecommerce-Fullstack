package com.backend.ecommerce.application.service;

import com.backend.ecommerce.domain.model.Order;
import com.backend.ecommerce.domain.model.enums.OrderStatus;
import com.backend.ecommerce.domain.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    public ResponseEntity<?> changeStatus(Integer id, OrderStatus status) {
        Optional<Order> orderBD = orderRepository.findById(id);
        if(orderBD.isEmpty()) {
            return new ResponseEntity<>("Orden "+id+" no fue encontrada. Verifique sus datos.",HttpStatus.NOT_FOUND);
        }
        Order newSave = orderBD.get();
        newSave.setOrderStatus(status);
        return ResponseEntity.ok(orderRepository.save(newSave));
    }

    public ResponseEntity<?> guardar(Order order) {
        if(validateOrder(order)) {
            order.getOrderProducts().forEach(orderProduct -> orderProduct.setOrder(order));
            return new ResponseEntity<>(orderRepository.save(order),HttpStatus.CREATED);
        }

        return new ResponseEntity<>("La orden no se pudo realizar, verifique los datos.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Order> buscarPor_ID(Integer id) {
        try {
            Optional<Order> orderDB = orderRepository.findById(id);
            if(orderDB.isPresent()) {
                return ResponseEntity.ok(orderDB.get());
            }
        }catch (Exception ex) {
            return new ResponseEntity<>(new Order(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Order(),HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Order>> obtenerTodas() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    public ResponseEntity<List<Order>> buscarPor_Usuario(Integer user_id) {
        return ResponseEntity.ok(orderRepository.findByUser(user_id));
    }
    public ResponseEntity<String> eliminar(Integer order_id) {
        Optional<Order> orderBD = orderRepository.findById(order_id);
        if(orderBD.isEmpty()) {
            return new ResponseEntity<>("No se ha encontrado la orden con ID "+order_id,HttpStatus.NOT_FOUND);
        }
        Order completeOrder = orderBD.get();
        completeOrder.getOrderProducts().forEach(orderProduct -> orderProduct.setOrder(new Order()));

        return ResponseEntity.ok("Orden eliminada correctamente");
    }

    private boolean validateOrder(Order order) {
        return Objects.nonNull(order.getUser()) && !order.getOrderProducts().isEmpty()
                && order.getFechaCreacion().isBefore(order.getFechaEntrega());
    }
}
