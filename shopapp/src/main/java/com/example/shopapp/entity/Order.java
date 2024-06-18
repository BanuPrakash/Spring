package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private Date orderDate = new Date(); // system date

    @ManyToOne
    @JoinColumn(name = "customer_fk")
    private Customer customer; // order is placed by

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_fk")
    private List<LineItem> items = new ArrayList<>();

    private double total;
}
