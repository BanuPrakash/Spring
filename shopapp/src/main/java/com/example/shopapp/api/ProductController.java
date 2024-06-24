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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import  static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

    @GetMapping("/hateoas/{pid}")
    public ResponseEntity<EntityModel<Product>> getProductHateoas(@PathVariable("pid") int id) throws EntityNotFoundException {
        Product p =  service.findProductById(id);
        Link selfLink = linkTo(methodOn(ProductController.class).getProductHateoas(id)).withSelfRel();
        Affordance update = afford(methodOn(ProductController.class).updateProduct(id, null));
        Affordance delete = afford(methodOn(ProductController.class).delete(id));

        Link productsLink = linkTo(methodOn(ProductController.class).getProducts(0,0)).withRel("products");
        EntityModel<Product> entityModel = EntityModel.of(p, selfLink.andAffordance(update), selfLink.andAffordance(delete), productsLink);
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/{pid}")
    public Product getProduct(@PathVariable("pid") int id) throws EntityNotFoundException {
        return service.findProductById(id);
    }

    @GetMapping("/etag/{pid}")
    public ResponseEntity<Product> getProductEtag(@PathVariable("pid") int id) throws EntityNotFoundException {
        Product p =   service.findProductById(id);
        return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
    }

    @Cacheable(value = "productCache", key = "#id")
    @GetMapping("/cache/{pid}")
    public Product getProductCache(@PathVariable("pid") int id) throws EntityNotFoundException {
        System.out.println("Cache Miss...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) { ex.printStackTrace(); }
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
    @CacheEvict(value = "productCache", key = "#id")
    public StringMessage delete(@PathVariable("id") int id) {
        return  new StringMessage("deleted !!!");
    }

    @PutMapping("/{id}")
    @CachePut(value = "productCache", key = "#id")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p)  throws EntityNotFoundException {
        return  service.updateProduct(id, p.getPrice());
    }
}
