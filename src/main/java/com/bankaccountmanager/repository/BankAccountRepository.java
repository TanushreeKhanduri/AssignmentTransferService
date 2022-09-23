package com.bankaccountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankaccountmanager.domain.model.BankAccount;

import java.math.BigDecimal;

/**
 * repository of bank_account table
 */
@Transactional
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update BankAccount b set b.currentBalance = b.currentBalance - :amount where b.id = :bankAccountId")
    int decreaseCurrentBalance(@Param("bankAccountId") Long bankAccountId, @Param("amount") BigDecimal amount);

    @Modifying(clearAutomatically = true)
    @Query("update BankAccount b set b.currentBalance = b.currentBalance + :amount where b.id = :bankAccountId")
    int increaseCurrentBalance(@Param("bankAccountId") Long bankAccountId, @Param("amount") BigDecimal amount);
}
