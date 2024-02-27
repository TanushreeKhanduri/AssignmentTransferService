package com.transfer.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is an integration class for rest services
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private String iban;
    private long cif;
}
