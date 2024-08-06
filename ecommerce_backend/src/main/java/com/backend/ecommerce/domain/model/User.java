package com.backend.ecommerce.domain.model;


import com.backend.ecommerce.domain.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50,nullable = false)
    private String username;
    @Column(length = 50,nullable = false)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    @Column(length = 50,nullable = false,unique = true)
    private String email;
    @Column(length = 100,nullable = false)
    private String address;
    @Column(length = 30,nullable = false)
    private String phone;
    @Column(nullable = false,unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
    @CreationTimestamp //Se pone automaticamente la fecha de creacion de esta entidad
    private LocalDateTime dateCreated;
    @UpdateTimestamp //Se pone automaticamente la fecha de la ultima actualizacion
    private LocalDateTime dateUpdated;
}
