package com.bankaccountmanager.converter;


import com.bankaccountmanager.domain.dto.CustomerDto;
import com.bankaccountmanager.domain.model.Customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * CustomerDto to Customer converter
 */
@Component
public class DtoToCustomerConverter{

    
    public static Customer convert(CustomerDto customerDto) {

        return Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .initial(customerDto.getInitial())
                .email(customerDto.getEmail())
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }

}
