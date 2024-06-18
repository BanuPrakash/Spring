package com.example.shopapp.dto;

import java.util.Date;

public record Report(String email, String firstName, String lastName, Date orderDate, double total) {
}
