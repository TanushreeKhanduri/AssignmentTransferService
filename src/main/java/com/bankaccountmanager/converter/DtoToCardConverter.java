package com.bankaccountmanager.converter;

import com.bankaccountmanager.domain.dto.CardDto;
import com.bankaccountmanager.domain.model.Card;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * CardDto to Card converter
 */
@Component
public class DtoToCardConverter {

    
    public Card convert(CardDto cardDto) {

        return Card.builder()
                .cardType(cardDto.getCardType())
                .number(cardDto.getNumber())
                .expiryDate(cardDto.getExpiryDate())
                .cvv(cardDto.getCvv())
                .build();
    }

}
