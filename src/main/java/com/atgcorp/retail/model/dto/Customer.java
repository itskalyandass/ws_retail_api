package com.atgcorp.retail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private Long id;
    private String name;
    private List<Purchase> purchases;
}
