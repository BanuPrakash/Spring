package com.example.shopapp.api;

import com.example.shopapp.dto.Report;
import com.example.shopapp.entity.Order;
import com.example.shopapp.service.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Service API")
@SecurityRequirement(name = "BearerAuth")
public class OrderController {
    private final OrderService service;

    @Hidden
    @PostMapping()
    public String placeOrder(@RequestBody Order order) {
        return service.placeOrder(order);
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.getOrders();
    }

    // Default we don't have HttpMessageConverter for String --> Date
    @GetMapping("/report")
    public List<Report> getReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return service.getReport(date);
    }
}
