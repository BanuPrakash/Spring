package com.example.shopapp.api;

import com.example.shopapp.ShopappApplication;
import com.example.shopapp.entity.Product;
import com.example.shopapp.security.service.JwtService;
import com.example.shopapp.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.CoreMatchers.*;
import  static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import  static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@WebMvcTest(ProductController.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = ShopappApplication.class)
public class ProductControllerTest {
    @MockBean
    private OrderService service;

     @Autowired
    private MockMvc mvc; // to perform CRUD

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    @Test
    public void getProductsTest() throws Exception {
        List<Product> products = Arrays.asList(
                Product.builder().id(1).name("A").price(100).quantity(200).build(),
                Product.builder().id(2).name("B").price(900).quantity(200).build());
        // mocking
        when(service.getAllProducts()).thenReturn(products);

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("A")));

        verify(service,times(1)).getAllProducts();
    }

    @Test
    public void addProductTest() throws Exception {
        Product p =  Product.builder().name("A").price(100).quantity(200).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p);

        when(service.saveProduct(Mockito.any(Product.class))).thenReturn(Mockito.any(Product.class));

        mvc.perform(post("/api/products")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(service, times(1)).saveProduct(Mockito.any(Product.class));
    }

    @Test
    public void addProductTestException() throws Exception {
        Product p =  Product.builder().name("").price(-9).quantity(0).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p);

//        when(service.saveProduct(Mockito.any(Product.class))).thenReturn(Mockito.any(Product.class));

        mvc.perform(post("/api/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("Name is required")));

        verifyNoInteractions(service);
    }
}
