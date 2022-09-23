package com.bankaccountmanager.converter;

import com.bankaccountmanager.domain.dto.BankAccountDto;
import com.bankaccountmanager.domain.dto.CardDto;
import com.bankaccountmanager.domain.model.BankAccount;
import com.bankaccountmanager.domain.model.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * BankAccountDto to BankAccount converter
 */
@Component
public class DtoToBankAccountConverter {

    public static BankAccount convert(BankAccountDto bankAccount) {
    	
    	Card cardFromBankAccount = new Card();
    	cardFromBankAccount.setCardType(bankAccount.getCard().getCardType());
    	cardFromBankAccount.setCvv(bankAccount.getCard().getCvv());
    	cardFromBankAccount.setExpiryDate(bankAccount.getCard().getExpiryDate());
    	cardFromBankAccount.setNumber(bankAccount.getCard().getNumber());

    	
        return BankAccount.builder()
                .iban(bankAccount.getIban())
                .currentBalance(bankAccount.getBalance())
                .card(cardFromBankAccount)
                .build();
    }

}
