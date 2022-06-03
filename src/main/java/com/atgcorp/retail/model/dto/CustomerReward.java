package com.atgcorp.retail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerReward {
    private Long id;
    private String name;
    private Reward reward;
}
