package com.transfer.converter;


import org.springframework.stereotype.Component;

import com.transfer.request.dto.AccountRequest;
import com.transfer.request.dto.TransferRequest;

/**
 * CustomerDto to Account converter
 */
@Component
public class TransferToAccountConverter{

    
    public static AccountRequest convert(TransferRequest request) {

        return AccountRequest.builder()
                .iban(request.getToAccount().getIban())
                .cif(request.getToAccount().getCif())
                .amount(request.getAmount())
                .build();
    }

}
