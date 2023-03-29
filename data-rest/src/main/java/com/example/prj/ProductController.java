package com.example.prj;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.prj.entity.Product;
import com.example.prj.entity.dao.ProductDao;

@BasePathAwareController
public class ProductController {
	@Autowired
	ProductDao productDao;
	
	@RequestMapping(path = "products", method = RequestMethod.GET)
	public @ResponseBody List<Product> getProducts() {
		return Arrays.asList(Product.builder().id(1).name("A").price(1111).quantity(1).build());
	}
	
}
