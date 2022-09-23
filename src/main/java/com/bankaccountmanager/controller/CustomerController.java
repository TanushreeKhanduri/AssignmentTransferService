package com.bankaccountmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bankaccountmanager.converter.CustomerToDtoConverter;
import com.bankaccountmanager.converter.DtoToCustomerConverter;
import com.bankaccountmanager.domain.dto.CustomerDto;
import com.bankaccountmanager.domain.model.Customer;
import com.bankaccountmanager.service.CustomerService;
import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class is created to manage customer process
 */
@Api("Customer management services")
@RestController
@RequestMapping(CustomerController.SERVICE_PATH)
public class CustomerController {

    public static final String SERVICE_PATH = "api/customer";
    public static final String METHOD_GET_ALL = "/all";

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public String saveCustomer(@ApiParam(value = "Details of customer") @RequestBody @Valid CustomerDto customerDto) {
        
    	Preconditions.checkNotNull(customerDto, "customerDto can not be null");
        Customer customer = customerService.saveCustomer(DtoToCustomerConverter.convert(customerDto));
        return customer.getFirstName() + " saved with ID " + customer.getId();
    }

    @ApiOperation(value = "Retrieves all customers", response = CustomerDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @GetMapping(value = METHOD_GET_ALL)
    public List<CustomerDto> getAllCustomers() {

        return customerService.getCustomerList().stream()
                .map(customer -> CustomerToDtoConverter.convert(customer))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve customer by given customerId", response = CustomerDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @GetMapping(value = "/{customerId}")
    public CustomerDto getCustomer(@ApiParam(value = "The ID of the customer") @PathVariable(name = "customerId") Long customerId) {
        
    	Customer customer = customerService.getCustomer(customerId);
        return CustomerToDtoConverter.convert(customer);
    }

}
