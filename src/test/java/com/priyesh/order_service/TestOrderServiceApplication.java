package com.priyesh.order_service;

import org.springframework.boot.SpringApplication;

import com.priyesh.order.OrderServiceApplication;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
