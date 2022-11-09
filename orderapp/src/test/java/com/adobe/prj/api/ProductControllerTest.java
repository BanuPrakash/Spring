package com.adobe.prj.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@MockBean
	private OrderService service;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = new ArrayList<>();
		products.add(Product.builder().id(1).name("A").price(100.00).quantity(100).build());
		products.add(Product.builder().id(2).name("B").price(100.00).quantity(100).build());
		
		when(service.getProducts()).thenReturn(products); // mocking
		
		mockMvc.perform(get("/api/products"))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$", hasSize(2)))
		 .andExpect(jsonPath("$[0].name", is("A")));
	
		verify(service,times(1)).getProducts();
	}
	
	@Test
	public void addProductTest() throws Exception {
		Product p = Product.builder().id(1).name("A").price(100.00).quantity(100).build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
		when(service.insertProduct(Mockito.any(Product.class))).thenReturn(Mockito.any(Product.class));
		
		mockMvc.perform(post("/api/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated());
		
		verify(service,times(1)).insertProduct(Mockito.any(Product.class));
	}
	
	@Test
	public void addProductExceptionTest() throws Exception {
		Product p = Product.builder().id(1).name("").price(5.00).quantity(-2).build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
		mockMvc.perform(post("/api/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(jsonPath("$.errors", hasSize(3)))
		.andExpect(jsonPath("$.errors", hasItem("Name is required")));
		
		verifyNoInteractions(service);
	}
}
