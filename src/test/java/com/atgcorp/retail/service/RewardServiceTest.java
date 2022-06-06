package com.atgcorp.retail.service;

import com.atgcorp.retail.model.dto.CustomerReward;
import com.atgcorp.retail.model.entity.Customer;
import com.atgcorp.retail.model.entity.Purchase;
import com.atgcorp.retail.repository.CustomerRepository;
import com.atgcorp.retail.util.CustomMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {

    @InjectMocks
    private RewardService rewardService;

    @Mock
    CustomMapper customMapper;

    @Mock
    private CustomerRepository customerRepository;

    private List<Customer> getCustomerEntity() {
        return new ArrayList<>() {{
            add(new Customer(1L, "test", new ArrayList<>() {{
                add(new Purchase(1L, 110.00, null, OffsetDateTime.now()));
            }}));
        }};
    }

    private List<com.atgcorp.retail.model.dto.Customer> getCustomerDTO() {
        return new ArrayList<>() {{
            add(new com.atgcorp.retail.model.dto.Customer(1L, "test", new ArrayList<>() {{
                add(new com.atgcorp.retail.model.dto.Purchase(1L, 110.00, null, OffsetDateTime.now()));
            }}));
        }};
    }

    private List<Customer> getCustomerEntityNoPurchase() {
        return new ArrayList<>() {{
            add(new Customer(1L, "test", null));
        }};
    }

    private List<com.atgcorp.retail.model.dto.Customer> getCustomerDTONoPurchase() {
        return new ArrayList<>() {{
            add(new com.atgcorp.retail.model.dto.Customer(1L, "test", null));
        }};
    }
    
    private List<CustomerReward> getCustomerRewardDTO() {
        return new ArrayList<>() {{
            add(new CustomerReward(1L, "test", null));
        }};
    }

    @Test
    void test_getAllRewardsPerCustomer_success() {
        var entities = getCustomerEntity();
        var customerDTOs = getCustomerDTO();
        when(customerRepository.findAll()).thenReturn(entities);
        when(customMapper.toCustomerDTOS(entities)).thenReturn(customerDTOs);
        when(customMapper.toCustomerRewards(customerDTOs)).thenReturn(getCustomerRewardDTO());

        var response = rewardService.getAllRewardsPerCustomer(Optional.empty());
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getId());
        assertEquals("test", response.get(0).getName());
        assertNotNull(response.get(0).getReward());
        assertEquals(70, response.get(0).getReward().getTotal());
        assertNotNull(response.get(0).getReward().getRewardByMonths());
        assertEquals(1, response.get(0).getReward().getRewardByMonths().size());
        assertEquals(70, response.get(0).getReward().getRewardByMonths().get(0).getTotal());
    }
    
    @Test
    void test_getAllRewardsPerCustomer_success_nopurchase() {
        var entities = getCustomerEntityNoPurchase();
        var customerDTOs = getCustomerDTONoPurchase();
        when(customerRepository.findAll()).thenReturn(entities);
        when(customMapper.toCustomerDTOS(entities)).thenReturn(customerDTOs);
        when(customMapper.toCustomerRewards(customerDTOs)).thenReturn(getCustomerRewardDTO());

        var response = rewardService.getAllRewardsPerCustomer(Optional.empty());
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getId());
        assertEquals("test", response.get(0).getName());
        assertNotNull(response.get(0).getReward());
        assertEquals(0, response.get(0).getReward().getTotal());
        assertNull(response.get(0).getReward().getRewardByMonths());
    }
}
