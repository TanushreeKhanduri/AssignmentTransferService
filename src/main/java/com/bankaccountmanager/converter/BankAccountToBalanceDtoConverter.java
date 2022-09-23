package com.bankaccountmanager.converter;

import com.bankaccountmanager.domain.dto.BalanceDto;
import com.bankaccountmanager.domain.model.BankAccount;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * BankAccount to BalanceDto converter
 */
@Component
public class BankAccountToBalanceDtoConverter {

    
    public static BalanceDto convert(BankAccount bankAccount) {
        return BalanceDto.builder()
                .bankAccountId(bankAccount.getId())
                .currentBalance(bankAccount.getCurrentBalance())
                .build();
    }

}
