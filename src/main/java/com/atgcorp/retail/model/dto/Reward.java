package com.atgcorp.retail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reward {
    Integer total;
    List<RewardByMonth> rewardByMonths;
}
