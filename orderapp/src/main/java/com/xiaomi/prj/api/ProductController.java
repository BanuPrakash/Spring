package com.xiaomi.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaomi.prj.entity.Product;
import com.xiaomi.prj.service.OrderService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private OrderService service;
	
	//GET http://localhost:8080/api/products
	// returned List<Product> is given to Jackson a HttpMessageConvertor ==> JSON
	@GetMapping()
	public List<Product> getProducts() {
		return service.getProducts();
	}
	
	//GET http://localhost:8080/api/products/4
	@GetMapping("/{pid}")
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) {
		return service.getProductById(id);
	}
	
	//POST http://localhost:8080/api/products
	// @RequestBody is to specify that JSON to Java conversion is required
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product p) {
		return new ResponseEntity<Product>(service.addProduct(p), HttpStatus.CREATED);
	}
	
	@PutMapping("/{pid}")
	public Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) {
		service.updateProduct(id, p.getPrice());
		return this.getProduct(id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		//
		return "Deleted product with id " + id;
	}
}
