package com.bankaccountmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bankaccountmanager.converter.BankAccountToBalanceDtoConverter;
import com.bankaccountmanager.converter.DtoToBankAccountConverter;
import com.bankaccountmanager.domain.dto.BalanceDto;
import com.bankaccountmanager.domain.dto.BankAccountDto;
import com.bankaccountmanager.domain.model.BankAccount;
import com.bankaccountmanager.service.BankAccountService;
import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * This class is created to manage bank account process
 */
@Api("Bank account services")
@SwaggerDefinition(tags = {
        @Tag(name = "Bank Account API", description = "Bank Account API")
})
@RestController
@RequestMapping(BankAccountController.SERVICE_PATH)
public class BankAccountController {

    public static final String SERVICE_PATH = "api/bank/account";
    public static final String METHOD_GET_BALANCE = "/balance";
    private static final String METHOD_GET_BALANCE_WITH_PARAM = "/balance/{bankAccountId}";
    public static final String METHOD_GET_BALANCE_ALL = "/balance/all";

    @Autowired
    private BankAccountService bankAccountService;

    @Operation(summary = "Create a "
    		+ "new bank account with a credit card or debit card by given customerId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @PutMapping(value = "{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String saveAccount(@ApiParam(value = "The ID of the customer") @PathVariable(name = "customerId") Long customerId,
                            @ApiParam(value = "The number of the customer") @RequestBody BankAccountDto bankAccountDto) {
       

        Preconditions.checkArgument(bankAccountDto != null, "bankAccountDto can not be null");
        Preconditions.checkArgument(customerId != null && customerId != 0, "Customer Id can not be null");
        Preconditions.checkArgument(bankAccountDto.getCard() != null, "bankAccountDto.card can not be null");
        
        BankAccount bankAccount = DtoToBankAccountConverter.convert(bankAccountDto);

        BankAccount ba = bankAccountService.addBankAccount(customerId, bankAccount);
        return " Bank Account created for " + ba.getCustomer().getFirstName() + " with Account ID " +
        		ba.getId();
    }

    @ApiOperation(value = "Retrieves the current balances of all bank accounts", response = BalanceDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @GetMapping(value = METHOD_GET_BALANCE_ALL)
    public List<BalanceDto> getAllBalances() {

        List<BankAccount> bankAccounts = bankAccountService.getBankAccountList();
        List<BalanceDto> accountBalances = bankAccounts.stream()
                .map(obj -> BankAccountToBalanceDtoConverter.convert(obj))
                .collect(Collectors.toList());
        return accountBalances;
    }

    @ApiOperation(value = "Retrieves the current balance of a bank account", response = BalanceDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @GetMapping(value = METHOD_GET_BALANCE_WITH_PARAM)
    public BalanceDto getBalance(@ApiParam(value = "The ID of the bank account") @PathVariable(name = "bankAccountId") Long bankAccountId) {

        BankAccount bankAccount = bankAccountService.getBankAccount(bankAccountId);
        return BankAccountToBalanceDtoConverter.convert(bankAccount);
    }

}
