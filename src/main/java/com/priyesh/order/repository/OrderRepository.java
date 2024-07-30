package com.priyesh.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyesh.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	
}
