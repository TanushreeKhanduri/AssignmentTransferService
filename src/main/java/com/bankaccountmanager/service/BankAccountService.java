package com.bankaccountmanager.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccountmanager.domain.model.BankAccount;
import com.bankaccountmanager.domain.model.Card;
import com.bankaccountmanager.domain.model.Customer;
import com.bankaccountmanager.exception.BankAccountManagerException;
import com.bankaccountmanager.repository.BankAccountRepository;
import com.bankaccountmanager.repository.CardRepository;
import com.bankaccountmanager.util.FormatterUtil;
import com.google.common.base.Preconditions;


/**
 * BankAccount management service
 */
@Service
public class BankAccountService {

    private static final String MESSAGE_FORMAT_NO_BANK_ACCOUNT = "No bankAccount by bankAccountId: %s";

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerService customerService;

    public BankAccount addBankAccount(Long customerId, BankAccount bankAccount) {
    	
        Preconditions.checkNotNull(bankAccount, "bankAccount can not be null");
        Preconditions.checkNotNull(bankAccount.getCard().getCardType(), "Card Type should be CREDIT_CARD or DEBIT_CARD");
        Preconditions.checkNotNull(bankAccount.getCurrentBalance(), "currentBalance can not be null");
        Preconditions.checkArgument(bankAccount.getCurrentBalance().compareTo(BigDecimal.ZERO) > -1, "CurrentBalance can not be negative");


        
        Customer customer = customerService.getCustomer(customerId);
        bankAccount.setCustomer(customer);


        Preconditions.checkArgument(customer != null && customer.getFirstName() != null, "No Customer found. "
        		+ "Please check Id of the customer for opening bank account");
        
        Card card = bankAccount.getCard();
        card.setHolderName(FormatterUtil.getCardHolderName(customer));

        // a workaround
        bankAccount.setCard(null);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        card.setBankAccount(savedBankAccount);
        cardRepository.save(card);

        return savedBankAccount;
    }

    public BankAccount getBankAccount(Long bankAccountId) {
        Preconditions.checkNotNull(bankAccountId, MESSAGE_FORMAT_NO_BANK_ACCOUNT, bankAccountId);

        return bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> BankAccountManagerException.to(MESSAGE_FORMAT_NO_BANK_ACCOUNT, bankAccountId));
    }


    public List<BankAccount> getBankAccountList() {
        return bankAccountRepository.findAll();
    }

    public BankAccount decreaseCurrentBalance(BankAccount bankAccount, BigDecimal amount) {
        int effectedRows = bankAccountRepository.decreaseCurrentBalance(bankAccount.getId(), amount);
        if (effectedRows == 0) {
            throw BankAccountManagerException.to(
                    "The bank account is not effected of withdraw");
        }

        return bankAccountRepository.findById(bankAccount.getId())
                .orElseThrow(() -> BankAccountManagerException.to(MESSAGE_FORMAT_NO_BANK_ACCOUNT, bankAccount.getId()));
    }

    public BankAccount increaseCurrentBalance(BankAccount bankAccount, BigDecimal amount) {
        int effectedRows = bankAccountRepository.increaseCurrentBalance(bankAccount.getId(), amount);
        if (effectedRows == 0) {
            throw BankAccountManagerException.to(
                    "The bank account is not effected of transfer");
        }

        return bankAccountRepository.findById(bankAccount.getId())
                .orElseThrow(() -> BankAccountManagerException.to(MESSAGE_FORMAT_NO_BANK_ACCOUNT, bankAccount.getId()));
    }
}
