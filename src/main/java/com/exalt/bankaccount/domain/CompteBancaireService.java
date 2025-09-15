package com.exalt.bankaccount.domain;

import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.domain.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompteBancaireService {

    public void deposer(CompteBancaire compte, BigDecimal montant) {
        compte.setSolde(compte.getSolde().add(montant));
        compte.getTransactions().add(new Transaction(null, TransactionType.DEPOT, montant, LocalDateTime.now(), compte));
    }

    public void retirer(CompteBancaire compte, BigDecimal montant) {
        if (compte.getSolde().compareTo(montant) < 0) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        compte.setSolde(compte.getSolde().subtract(montant));
        compte.getTransactions().add(new Transaction(null, TransactionType.RETRAIT, montant, LocalDateTime.now(), compte));
    }
}
