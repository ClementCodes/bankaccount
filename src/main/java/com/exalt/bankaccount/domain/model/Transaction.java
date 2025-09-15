package com.exalt.bankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long id;
    private TransactionType type;
    private BigDecimal montant;
    private LocalDateTime date;
    private CompteBancaire compteBancaire;

}
