package com.bankaccountmanager.converter;

import org.springframework.stereotype.Component;

import com.bankaccountmanager.domain.dto.CardDto;
import com.bankaccountmanager.domain.model.Card;

/**
 * Card to CardDto converter
 */
@Component
public class CardToDtoConverter {

    
    public CardDto convert(Card card) {

        return CardDto.builder()
                .cardType(card.getCardType())
                .number(card.getNumber())
                .expiryDate(card.getExpiryDate())
                .cvv(card.getCvv())
                .build();
    }

}
