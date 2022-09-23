package com.bankaccountmanager.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccountmanager.domain.model.BankAccount;

import lombok.AllArgsConstructor;

/**
 * Withdraw process management service
 */
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class WithdrawService {

    private TransactionService transactionService;
    private BankAccountService bankAccountService;

    public void withdraw(Long bankAccountId, BigDecimal amount) {
        BankAccount bankAccount = bankAccountService.getBankAccount(bankAccountId);

        transactionService.executeWithdraw(bankAccount, amount);
    }

}
