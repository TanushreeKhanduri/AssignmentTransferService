package com.bankaccountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankaccountmanager.domain.model.TransactionHistory;


/**
 * transaction_history of bank_account table
 */
@Transactional
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
