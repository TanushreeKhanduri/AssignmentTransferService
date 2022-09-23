package com.bankaccountmanager.domain.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * bank_account table java object
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
@EntityListeners(AuditingEntityListener.class)
public class BankAccount {

    @Id
    @GenericGenerator(name = "bank_account_generator", strategy = "increment")
    @GeneratedValue(generator = "bank_account_generator")
    private Long id;

    private String iban;

    @NonNull
    private BigDecimal currentBalance;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

   
    @OneToOne(optional = false, cascade = CascadeType.ALL, mappedBy = "bankAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private Card card;
}
