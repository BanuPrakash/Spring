package com.example.shopapp.api;

import com.example.shopapp.entity.Product;
import com.example.shopapp.service.EntityNotFoundException;
import com.example.shopapp.service.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
        if ((low == 0.0) && (high == 0.0)) {
            return service.getAllProducts();
        } else {
            return service.findProductRange(low, high);
        }
    }
    // GET http://localhost:8080/api/products/3
    // Path parameters

    @Operation(
            description = "Service that return a Product",
            summary = "This service returns a Product by the ID",
            responses = {
                    @ApiResponse(description = "Successful Operation", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "404", description = "Product  Not found", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{pid}")
    public Product getProduct(@PathVariable("pid") int id) throws EntityNotFoundException {
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
    public Product addProduct(@Valid @RequestBody Product p) {
         return  service.saveProduct(p);
    }

    @Hidden
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        return  "deleted !!!";
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p)  throws EntityNotFoundException {
        return  service.updateProduct(id, p.getPrice());
    }
}
