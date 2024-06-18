package com.example.shopapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
