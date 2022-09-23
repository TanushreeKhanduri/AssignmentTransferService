package com.bankaccountmanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankaccountmanager.domain.model.Customer;


/**
 * customer of bank_account table
 */
@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
