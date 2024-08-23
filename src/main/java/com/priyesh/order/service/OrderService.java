package com.priyesh.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.priyesh.order.client.InventoryClient;
import com.priyesh.order.dto.OrderRequest;
import com.priyesh.order.model.Order;
import com.priyesh.order.repository.OrderRepository;
import com.priyesh.order.config.RestClientComponents;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository OrderRepository;
	private final RestClientComponents RestClientComponents;
	private final InventoryClient inventoryClient;
	
	public String placeOrder(OrderRequest orderRequest)
	{
		var isProductInStock = /*RestClientComponents.getInventoryClient()*/inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		
		if(isProductInStock)
		{
			Order order = new Order();
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setPrice(orderRequest.price());
			order.setSkuCode(orderRequest.skuCode());
			order.setQuantity(orderRequest.quantity());
			OrderRepository.save(order);
			return "Order placed Successfully";
		}
		else
		{
			return "Product with skuCode " + orderRequest.skuCode() + " is not in Stock";
		}
	}

}
