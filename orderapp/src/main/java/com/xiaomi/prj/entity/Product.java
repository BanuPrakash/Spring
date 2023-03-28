package com.xiaomi.prj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="Name is required")
	private String name;
	
	@Min(value = 10, message="Price ${validatedValue} should be more than {value}")
	@Column(name="amount")
	private double price;

	@Min(value = 0, message="Quantity ${validatedValue} should be more than {value}")
	private int quantity; // inventory
}
