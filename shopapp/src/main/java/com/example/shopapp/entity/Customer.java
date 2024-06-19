package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="customers")
public class Customer {
    @Id
    private String email;

    @Column(name="FNAME", length = 100)
    private String firstName;

    @Column(name="LNAME", length = 100)
    private String lastName;

    // By-directional relationship
   // @OneToMany(mappedBy = "customer")
    // List<Order> orders = new ArrayList<>();
}
