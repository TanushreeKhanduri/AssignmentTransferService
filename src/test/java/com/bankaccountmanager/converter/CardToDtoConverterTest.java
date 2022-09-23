package com.bankaccountmanager.converter;

import com.bankaccountmanager.domain.dto.CardDto;
import com.bankaccountmanager.domain.model.Card;
import com.bankaccountmanager.util.TestDataUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CardToDtoConverterTest {

    @InjectMocks
    private CardToDtoConverter cardToDtoConverter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvert() {
        Card card = TestDataUtils.getDebitCard1();
        CardDto cardDto = cardToDtoConverter.convert(card);
        Assert.assertNotNull(cardDto);
        Assert.assertEquals(card.getCardType(), cardDto.getCardType());
        Assert.assertEquals(card.getExpiryDate(), cardDto.getExpiryDate());
        Assert.assertEquals(card.getNumber(), cardDto.getNumber());
        Assert.assertEquals(card.getCvv(), cardDto.getCvv());
    }
}