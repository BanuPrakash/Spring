package com.example.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    int id;
    String title;
    List<String> skills = new ArrayList<>();
    Map<String, String> communication = new LinkedHashMap<>();
}

