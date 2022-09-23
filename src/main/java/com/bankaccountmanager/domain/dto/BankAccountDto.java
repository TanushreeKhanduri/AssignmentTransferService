package com.bankaccountmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This class is an integration class for rest services
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private String iban;
    private BigDecimal balance;
    private CardDto card;

}
