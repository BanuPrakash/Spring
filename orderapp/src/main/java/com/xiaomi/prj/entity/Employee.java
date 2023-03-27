package com.xiaomi.prj.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
	private int id;
	
	private String telephone;
	
	@Builder.Default
	private List<String> skills = new ArrayList<>();
	
}
