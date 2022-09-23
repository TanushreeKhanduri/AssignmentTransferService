package com.bankaccountmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bankaccountmanager.domain.dto.AmountDto;
import com.bankaccountmanager.service.WithdrawService;
import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

/**
 * This class is created to manage withdraw process
 */
@Api("Withdraw services")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping(WithdrawController.SERVICE_PATH)
public class WithdrawController {

    public static final String SERVICE_PATH = "api/withdraw";

    private WithdrawService withdrawService;

    @ApiOperation(value = "Withdraw from an account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @PostMapping(value = "{bankAccountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void re(@ApiParam(value = "The ID of the bank account") @PathVariable(name = "bankAccountId") Long bankAccountId,
                         @ApiParam(value = "The amount of the withdraw transaction") @RequestBody @Valid AmountDto amountDto) {
        Preconditions.checkNotNull(amountDto, "amountDto can not be null");
        withdrawService.withdraw(bankAccountId, amountDto.getAmount());
    }

}
