package com.atgcorp.retail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {
    private Long id;
    private Double amount;
    private Customer customerPurchase;
    private OffsetDateTime createTimestamp;
}