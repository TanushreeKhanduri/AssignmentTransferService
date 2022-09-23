package com.bankaccountmanager.converter;

import com.bankaccountmanager.domain.dto.CustomerDto;
import com.bankaccountmanager.domain.model.Customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Customer to CustomerDto converter
 */
@Component
public class CustomerToDtoConverter {

    
    public static CustomerDto convert(Customer customer) {

        return CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .initial(customer.getInitial())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

}
