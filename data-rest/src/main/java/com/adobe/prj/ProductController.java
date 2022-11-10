package com.adobe.prj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Product;

@BasePathAwareController
public class ProductController {
	@Autowired
	ProductDao productDao;
	
	@RequestMapping(path="products", method=RequestMethod.GET)
	public @ResponseBody List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		products.add(Product.builder().id(1).name("A").price(100.00).quantity(100).build());
		products.add(Product.builder().id(2).name("B").price(100.00).quantity(100).build());
	
		return products;
	}
}
