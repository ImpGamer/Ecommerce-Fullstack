package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    /*Atributo cascade que el indica que cuando una instancia "Order" en persistida en la DB a su vez tambien persistira
    * todos los elementos de la relacion especificada, en este caso todos los "OrderProducts"*/
    @OneToMany(mappedBy = "order",cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProducts;
}
