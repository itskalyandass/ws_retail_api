package com.atgcorp.retail.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMERID", nullable = false)
    private Long customerId;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "customerPurchase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Purchase> purchases;
}
