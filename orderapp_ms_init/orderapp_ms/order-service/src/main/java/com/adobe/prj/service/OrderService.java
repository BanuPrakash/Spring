package com.adobe.prj.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.adobe.prj.dao.OrderRepository;
import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());


        List<String> skuCodes = order.getItems().stream()
                .map(Item::getSkuCode)
                .collect(Collectors.toList());

            // stock
            InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
            
            for(int i = 0; i < inventoryResponsArray.length; i++) {
            	System.out.println(inventoryResponsArray[i].getSkuCode() + ", " + inventoryResponsArray[i].isInStock());
            }
            boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                    .allMatch(InventoryResponse::isInStock);
            
            if (allProductsInStock) {
                orderRepository.save(order);
                return "Order Placed Successfully";
            } else {
                throw new IllegalArgumentException("Product not in stock");
            }
    }

}