package com.transfer.request.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

	private BankAccountDto fromAccount;
	private BankAccountDto toAccount;
	private BigDecimal amount;
}
