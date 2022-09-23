package com.bankaccountmanager.service;

import com.bankaccountmanager.BankAccountManagerApplication;
import com.bankaccountmanager.domain.model.Customer;
import com.bankaccountmanager.exception.BankAccountManagerException;
import com.bankaccountmanager.repository.CustomerRepository;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Customer management service
 */
@Service
public class CustomerService {

    private static final String MESSAGE_FORMAT_NO_CUSTOMER = "No customer by customerId: %d";

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) {
    	
        Preconditions.checkNotNull(customerId, MESSAGE_FORMAT_NO_CUSTOMER, customerId);
        return customerRepository.findById(customerId)
                .orElseThrow(() -> BankAccountManagerException.to(MESSAGE_FORMAT_NO_CUSTOMER, customerId));
    }

    public Customer saveCustomer(Customer customer) {
        Preconditions.checkNotNull(customer, "customer can not be null");
        Preconditions.checkNotNull(customer.getFirstName(), "customer first name can not be null");
        
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }
}
