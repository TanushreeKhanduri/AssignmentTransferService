package com.bankaccountmanager.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.bankaccountmanager.domain.model.BankAccount;
import com.bankaccountmanager.domain.type.CardType;
import com.bankaccountmanager.domain.type.TransactionType;
import com.bankaccountmanager.exception.BankAccountManagerException;

/**
 * Transaction Fee management service
 */
@Service
public class TransactionFeeService {

    private static final MathContext mathContext = new MathContext(7, RoundingMode.HALF_UP);

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal creditCardTransferFeeMultiplier = BigDecimal.ONE.divide(ONE_HUNDRED, mathContext);
    private static final BigDecimal creditCardWithdrawFeeMultiplier = BigDecimal.ONE.divide(ONE_HUNDRED, mathContext);

    public BigDecimal getFee(TransactionType transactionType, BankAccount bankAccount, BigDecimal amount) {

        if (CardType.DEBIT_CARD.equals(bankAccount.getCard().getCardType())) {
            return BigDecimal.ZERO;
        }
        
        if (TransactionType.TRANSFER.equals(transactionType) && CardType.CREDIT_CARD.equals(bankAccount.getCard().getCardType())) {
            return creditCardTransferFeeMultiplier.multiply(amount, mathContext);
        } else if (TransactionType.WITHDRAW.equals(transactionType) && CardType.CREDIT_CARD.equals(bankAccount.getCard().getCardType())) {
            return creditCardWithdrawFeeMultiplier.multiply(amount, mathContext);
        }

        throw BankAccountManagerException.to("Unknown transactionType: %s", transactionType);
    }

    public BigDecimal getTotalAmount(BigDecimal amount, BigDecimal fee) {
        return amount.add(fee, mathContext);
    }

}
