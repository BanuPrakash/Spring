package com.example.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record Report(String email, String firstName, String lastName, @JsonFormat(pattern = "dd-MMM-yyyy") Date orderDate, double total) {
}
