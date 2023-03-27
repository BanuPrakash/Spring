package com.xiaomi.prj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.prj.entity.Product;

public class Test {

	public static void main(String[] args) throws Exception {
		Product p = Product.builder()
				.id(11)
				.name("iPhone 14")
				.price(120000.00)
				.quantity(100)
				.build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		System.out.println(json);
	}

}
