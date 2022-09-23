package com.bankaccountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankaccountmanager.domain.model.Card;

/**
 * card of bank_account table
 */
@Transactional
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
