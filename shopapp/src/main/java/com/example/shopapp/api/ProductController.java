package com.example.shopapp.api;

import com.example.shopapp.entity.Product;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService service;

    // GET http://localhost:8080/api/products
    // QueryParametes for sub-set / filter
    // GET http://localhost:8080/api/products?low=100&high=5000
    @GetMapping
    public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0") double low,
                                     @RequestParam(name = "high", defaultValue = "0") double high) {
        if((low == 0.0) && (high == 0.0)) {
            return service.getAllProducts();
        } else {
            return service.findProductRange(low, high);
        }
    }
    // GET http://localhost:8080/api/products/3
    // Path parameters
    @GetMapping("/{pid}")
    public Product getProduct(@PathVariable("pid") int id) {
        return service.findProductById(id);
    }

    // POST http://localhost:8080/api/products
    /* Payload:
        {
            name: "",
            price: xxx,
            quantity: 100
          }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Product addProduct(@RequestBody Product p) {
         return  service.saveProduct(p);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        return  "deleted !!!";
    }
}
