package com.transfer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.request.dto.TransferRequest;
import com.transfer.service.TransferService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class is created to manage customer process
 */
@Api("Transfer management services")
@RestController
@RequestMapping(TransferController.SERVICE_PATH)
public class TransferController {

    public static final String SERVICE_PATH = "api/transfer";

    @Autowired
    private TransferService transferService;

    /*
     * For the purpose of assignment we are only implementing transfer from one account to another within the same bank*/
    @ApiOperation(value = "Account to Account Transfer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "Internal Error.")
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public String transferMoney(@ApiParam(value = "Details of transfer") @RequestBody @Valid TransferRequest transferDto) {
    	transferService.tranfer(transferDto);
        return "Transfer Successful";
    }
}
