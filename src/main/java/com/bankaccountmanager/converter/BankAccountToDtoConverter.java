package com.bankaccountmanager.converter;

import org.springframework.stereotype.Component;

import com.bankaccountmanager.domain.dto.BankAccountDto;
import com.bankaccountmanager.domain.dto.CardDto;
import com.bankaccountmanager.domain.model.BankAccount;

/**
 * BankAccount to BankAccountDto converter
 */
@Component
public class BankAccountToDtoConverter {

    public static BankAccountDto convert(BankAccount bankAccount) {
    	CardDto cardDto = new CardDto(bankAccount.getCard().getCardType(), bankAccount.getCard().getNumber(),
    			bankAccount.getCard().getExpiryDate(), bankAccount.getCard().getCvv());
        return BankAccountDto.builder()
                .iban(bankAccount.getIban())
                .balance(bankAccount.getCurrentBalance())
                .card(cardDto)
                .build();
    }

}
