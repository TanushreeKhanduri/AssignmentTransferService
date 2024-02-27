package com.transfer.converter;


import org.springframework.stereotype.Component;

import com.transfer.request.dto.AccountRequest;
import com.transfer.request.dto.TransferRequest;

/**
 * CustomerDto to Account converter
 */
@Component
public class TransferRequestToAccountRequestConverter{

    
    public static AccountRequest convert(TransferRequest request) {

        return AccountRequest.builder()
                .iban(request.getFromAccount().getIban())
                .cif(request.getFromAccount().getCif())
                .amount(request.getAmount().negate())
                .build();
    }

}
