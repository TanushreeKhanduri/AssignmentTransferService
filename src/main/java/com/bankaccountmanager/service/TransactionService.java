package com.bankaccountmanager.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccountmanager.domain.model.BankAccount;
import com.bankaccountmanager.domain.model.TransactionHistory;
import com.bankaccountmanager.domain.type.StatementType;
import com.bankaccountmanager.domain.type.TransactionStatus;
import com.bankaccountmanager.domain.type.TransactionType;
import com.bankaccountmanager.exception.InsufficientBalanceManagerException;
import com.bankaccountmanager.repository.TransactionHistoryRepository;
import com.google.common.base.Preconditions;

/**
 * Transaction management service as TRANSFER and WITHDRAW
 */
@Service
public class TransactionService {

    @Autowired
    private ValidationService validationService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionFeeService transactionFeeService;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    public void executeWithdraw(BankAccount bankAccount, BigDecimal amount) {
    	
        Preconditions.checkNotNull(bankAccount, "bankAccount can not be null");
        validationService.validAmount(amount);

        TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.WITHDRAW,
                StatementType.EXPENSE,
                bankAccount,
                amount);
        try {

            takeMoney(transactionHistoryBuilder, bankAccount, amount);

        } catch (InsufficientBalanceManagerException e) {
            setTransactionHistoryBuilderAsFail(transactionHistoryBuilder, TransactionStatus.INSUFFICIENT_BALANCE, e.getMessage());
            throw e;

        } catch (RuntimeException e) {
            setTransactionHistoryBuilderAsFail(transactionHistoryBuilder, TransactionStatus.FAIL, e.getMessage());
            throw e;

        } finally {
        }
    }


    public void executeTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, final BigDecimal amount) {
        // validate parameters
        Preconditions.checkNotNull(fromBankAccount, "fromBbankAccount can not be null");
        Preconditions.checkNotNull(toBankAccount, "toBankAccount can not be null");
        Preconditions.checkArgument(!Objects.equals(fromBankAccount.getId(), toBankAccount.getId()),
                "Transfer can not executed an account to the same account. bankAccountId: ",
                fromBankAccount.getId());

        // create TransactionHistoryBuilder for fromBankAccount
        TransactionHistory.TransactionHistoryBuilder fromTransactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.TRANSFER,
                StatementType.EXPENSE,
                fromBankAccount,
                amount);

        // create TransactionHistoryBuilder for toBankAccount
        TransactionHistory.TransactionHistoryBuilder toTransactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.TRANSFER,
                StatementType.INCOME,
                toBankAccount,
                amount);

        try {
            validationService.validAmount(amount);

            takeMoney(fromTransactionHistoryBuilder, fromBankAccount, amount);
            putMoney(toTransactionHistoryBuilder, toBankAccount, amount);

        } catch (InsufficientBalanceManagerException e) {
            setTransactionHistoryBuilderAsFail(fromTransactionHistoryBuilder, TransactionStatus.INSUFFICIENT_BALANCE, e.getMessage());
            setTransactionHistoryBuilderAsFail(toTransactionHistoryBuilder, TransactionStatus.INSUFFICIENT_BALANCE, e.getMessage());
            throw e;

        } catch (RuntimeException e) {
            setTransactionHistoryBuilderAsFail(fromTransactionHistoryBuilder, TransactionStatus.FAIL, e.getMessage());
            setTransactionHistoryBuilderAsFail(toTransactionHistoryBuilder, TransactionStatus.FAIL, e.getMessage());
            throw e;

        } finally {
            transactionHistoryRepository.save(fromTransactionHistoryBuilder.build());
            transactionHistoryRepository.save(toTransactionHistoryBuilder.build());
        }
    }

    private void takeMoney(TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder, BankAccount bankAccount, BigDecimal amount) {
        BigDecimal fee = transactionFeeService.getFee(TransactionType.TRANSFER, bankAccount, amount);
        BigDecimal totalAmount = transactionFeeService.getTotalAmount(amount, fee);

        validationService.checkWithdrawable(bankAccount, totalAmount);

        BankAccount updatedBankAccount = bankAccountService.decreaseCurrentBalance(bankAccount, totalAmount);
        validationService.validateCurrentBalance(updatedBankAccount);

        transactionHistoryBuilder.status(TransactionStatus.SUCCESS)
                .fee(fee)
                .totalAmount(totalAmount)
                .afterBalance(updatedBankAccount.getCurrentBalance());
    }

    private void putMoney(TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder, BankAccount bankAccount, BigDecimal amount) {

        BankAccount updatedBankAccount = bankAccountService.increaseCurrentBalance(bankAccount, amount);

        transactionHistoryBuilder.status(TransactionStatus.SUCCESS)
                .fee(BigDecimal.ZERO)
                .totalAmount(amount)
                .afterBalance(updatedBankAccount.getCurrentBalance());
    }

    private TransactionHistory.TransactionHistoryBuilder getTransactionHistoryBuilder(
            TransactionType transactionType,
            StatementType statementType,
            BankAccount bankAccount,
            BigDecimal amount) {

        return TransactionHistory.builder()
                .type(transactionType)
                .statementType(statementType)
                .amount(amount)
                .customerId(bankAccount.getCustomer().getId())
                .bankAccountId(bankAccount.getId())
                .cardId(bankAccount.getCard().getId())
                .beforeBalance(bankAccount.getCurrentBalance());
    }

    private void setTransactionHistoryBuilderAsFail(
            TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder,
            TransactionStatus transactionStatus,
            String failingReason) {

        transactionHistoryBuilder.status(transactionStatus)
                .failingReason(failingReason);
    }

}
