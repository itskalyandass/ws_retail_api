package com.atgcorp.retail.util;

import com.atgcorp.retail.model.dto.Customer;
import com.atgcorp.retail.model.dto.CustomerReward;
import com.atgcorp.retail.model.dto.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    @Mapping(target = "id", source = "customer.customerId")
    Customer customerEntityToDTO(com.atgcorp.retail.model.entity.Customer customer);

    @Mappings({
            @Mapping(target = "id", source = "purchase.purchaseId"),
            @Mapping(target = "customerPurchase", ignore = true)
    })
    Purchase purchaseEntityToDTO(com.atgcorp.retail.model.entity.Purchase purchase);

    CustomerReward customerToCustomerReward(Customer customer);

    List<Purchase> toPurchaseDTOS(Collection<com.atgcorp.retail.model.entity.Purchase> purchase);

    List<Customer> toCustomerDTOS(Collection<com.atgcorp.retail.model.entity.Customer> customers);

    List<CustomerReward> toCustomerRewards(Collection<Customer> customers);
}
