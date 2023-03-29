package com.xiaomi.prj.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaomi.prj.entity.Product;
import com.xiaomi.prj.exceptions.ResourceNotFoundException;
import com.xiaomi.prj.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/products")
@Validated
@Tag(name="Products", description = "Product APIs")
public class ProductController {
	@Autowired
	private OrderService service;

	
	@Cacheable(value="productCache", key="#id")
	@GetMapping("/cache/{pid}")
	@Operation(summary = "Get Product by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found product for given ID")
	})
	public Product getProductCache(@PathVariable("pid") int id) throws ResourceNotFoundException {
		System.out.println("Cache Miss!!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return  service.getProductById(id);
	}
	
	// GET http://localhost:8080/api/products/cache/4
	@GetMapping("/etag/{pid}")
	public ResponseEntity<Product> getProductEtag(@PathVariable("pid") int id) throws ResourceNotFoundException {
		Product p =  service.getProductById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.getVer())).body(p);
	}

	// GET http://localhost:8080/api/products
	// GET http://localhost:8080/api/products?low=5000&high=50000
	// returned List<Product> is given to Jackson a HttpMessageConvertor ==> JSON
	@GetMapping()
	public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0.0") double low,
			@RequestParam(name = "high", defaultValue = "0.0") double high) {
		if (low == 0.0 && high == 0.0) {
			return service.getProducts();
		} else {
			return service.getByRange(low, high);
		}
	}

	// GET http://localhost:8080/api/products/4
	@GetMapping("/{pid}")
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) throws ResourceNotFoundException {
		return service.getProductById(id);
	}

	// POST http://localhost:8080/api/products
	// @RequestBody is to specify that JSON to Java conversion is required
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		return new ResponseEntity<Product>(service.addProduct(p), HttpStatus.CREATED);
	}

	@CachePut(value="productCache", key="#id")
	@PutMapping("/{pid}")
	public Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) throws ResourceNotFoundException {
		service.updateProduct(id, p.getPrice());
		return this.getProduct(id);
	}

	// avoid
	@CacheEvict(value="productCache", key="#id")
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		//
		return "Deleted product with id " + id;
	}
}
