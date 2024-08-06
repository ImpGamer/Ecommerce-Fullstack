package com.backend.ecommerce.domain.repositories;

import com.backend.ecommerce.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "SELECT * FROM orders WHERE usuario_id=:user_id", nativeQuery = true)
    List<Order> findByUser(@Param("user_id")Integer user_id);
}