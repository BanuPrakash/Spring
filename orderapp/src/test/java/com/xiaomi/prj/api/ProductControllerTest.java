package com.xiaomi.prj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.prj.entity.Product;
import com.xiaomi.prj.service.OrderService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	// to perform CRUD operations using HTTP methods
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private OrderService service;
	
	
	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = Arrays.asList(
				Product.builder().id(1).name("A").price(100).quantity(100).build(),
				Product.builder().id(2).name("B").price(200).quantity(100).build());
	
		// mocking
		when(service.getProducts()).thenReturn(products);
		
		mockMvc.perform(get("/api/products"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("A")))
			.andExpect(jsonPath("$[1].name", is("B")));
	
		verify(service, times(1)).getProducts();
	}
	
	@Test
	public void addProductTest() throws Exception {
		Product p = Product.builder().name("X").price(100).quantity(12).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
		when(service.addProduct(Mockito.any(Product.class)))
		.thenReturn(Mockito.any(Product.class));
		
		mockMvc.perform(post("/api/products").content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
		verify(service, times(1)).addProduct(Mockito.any(Product.class));
	}
	
	@Test
	public void addProductExceptionTest() throws Exception {
		Product p = Product.builder().name("").price(-99.0).quantity(88).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
		mockMvc.perform(post("/api/products").content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errors", hasSize(2)))
				.andExpect(jsonPath("$.errors", hasItem("Name is required")))
				.andExpect(jsonPath("$.errors", hasItem("Price -99.0 should be more than 10")));
		
		verifyNoInteractions(service);
	}
}
