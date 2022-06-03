package com.atgcorp.retail.repository;

import com.atgcorp.retail.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
