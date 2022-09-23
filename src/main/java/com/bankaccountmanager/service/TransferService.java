package com.bankaccountmanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccountmanager.domain.model.BankAccount;

import java.math.BigDecimal;

/**
 * Transfer process management service
 */
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class TransferService {

    private TransactionService transactionService;
    private BankAccountService bankAccountService;

    public void transfer(Long fromBankAccountId, Long toBankAccountId, BigDecimal amount) {
        BankAccount fromBankAccount = bankAccountService.getBankAccount(fromBankAccountId);
        BankAccount toBankAccount = bankAccountService.getBankAccount(toBankAccountId);

        transactionService.executeTransfer(fromBankAccount, toBankAccount, amount);
    }


}
