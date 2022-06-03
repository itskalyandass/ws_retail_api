package com.atgcorp.retail.controller;

import com.atgcorp.retail.model.dto.CustomerReward;
import com.atgcorp.retail.model.dto.Reward;
import com.atgcorp.retail.model.dto.RewardByMonth;
import com.atgcorp.retail.service.RewardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardControllerTest {

    @InjectMocks
    private RewardController rewardController;

    @Mock
    private RewardService rewardService;

    private List<CustomerReward> getCustomerRewardDTO() {
        return new ArrayList<>() {{
            add(new CustomerReward(1L, "test", new Reward(10, new ArrayList<>() {{
                add(new RewardByMonth("JUNE-2022", 10));
            }})));
        }};
    }

    @Test
    void test_getAllCustomersWithRewards_success() {
        when(rewardService.getAllRewardsPerCustomer()).thenReturn(getCustomerRewardDTO());
        var responseEntity = rewardController.getAllCustomersWithRewards();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().get(0).getId());
        assertEquals("test", responseEntity.getBody().get(0).getName());
        assertNotNull(responseEntity.getBody().get(0).getReward());
        assertEquals(10, responseEntity.getBody().get(0).getReward().getTotal());
        assertEquals(1, responseEntity.getBody().get(0).getReward().getRewardByMonths().size());
        assertEquals(10, responseEntity.getBody().get(0).getReward().getRewardByMonths().get(0).getTotal());
        assertEquals("JUNE-2022", responseEntity.getBody().get(0).getReward().getRewardByMonths().get(0).getMonth());
    }

    @Test
    void test_getAllCustomersWithRewards_no_content() {
        when(rewardService.getAllRewardsPerCustomer()).thenReturn(new ArrayList<>());
        var responseEntity = rewardController.getAllCustomersWithRewards();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
