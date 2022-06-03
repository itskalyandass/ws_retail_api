package com.atgcorp.retail.controller;

import com.atgcorp.retail.model.dto.CustomerReward;
import com.atgcorp.retail.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reward")
public class RewardController {

    @Autowired
    RewardService rewardService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerReward>> getAllCustomersWithRewards() {
        var customerRewards = rewardService.getAllRewardsPerCustomer();
        if (customerRewards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }
}
