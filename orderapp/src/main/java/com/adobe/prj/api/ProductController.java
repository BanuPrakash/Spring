package com.adobe.prj.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.NotFoundException;
import com.adobe.prj.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("api/products")
@Tag(name="products", description="Product APIs")
public class ProductController {
	@Autowired
	private OrderService service;
	
	
	// Accept: application/json
	// GET http://localhost:8080/api/products
	// GET http://localhost:8080/api/products?low=5000&high=50000
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if( low == 0.0 && high == 0.0) {
			return service.getProducts();
		}
		return service.getByRange(low, high);
	}
	
	// GET http://localhost:8080/api/products/3
	@Operation(summary = "Get Product by id")	
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@Cacheable(value="productCache", key ="#id")
	public @ResponseBody Product getProduct(@PathVariable("id") int id) throws NotFoundException { 
		System.out.println("Cache Miss!!!");
		try {
			Thread.sleep(4000);
		} catch(Exception ex) {}
		return service.getProductById(id);
	}
	
	@GetMapping("/cache/{id}")
	public ResponseEntity<Product> getProductETag(@PathVariable("id") int id) throws NotFoundException { 
		Product p = service.getProductById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
	}
	
	// Content-type:application/json
	// POST http://localhost:8080/api/products
	@PostMapping()
	@Cacheable(value="productCache", key="#p.id", condition = "#p.price > 5000")
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		service.insertProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}
	
	@CachePut(value="productCache", key="#id")
	@PutMapping("/{id}")
	public @ResponseBody Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) throws NotFoundException {
		return service.updatePrice(id, p.getPrice());
	}
	
	@GetMapping("/cache-clear/{id}")
	@CacheEvict(value="productCache", key="#id")
	public void clear(@PathVariable("id") int id) {
	}
}
