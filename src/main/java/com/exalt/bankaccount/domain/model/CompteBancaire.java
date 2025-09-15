package com.exalt.bankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor // C'est la ligne la plus importante : SANS accès privé !
@EqualsAndHashCode
public class CompteBancaire {

    private final Long id;
    private final BigDecimal solde;
    private final List<Transaction> transactions;

    public CompteBancaire() {
        this.id = null;
        this.solde = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public CompteBancaire deposer(BigDecimal montant) {
        BigDecimal nouveauSolde = this.solde.add(montant);
        List<Transaction> nouvellesTransactions = new ArrayList<>(this.transactions);
        CompteBancaire comptePourTransaction = new CompteBancaire(this.id, nouveauSolde, new ArrayList<>());
        nouvellesTransactions
                .add(new Transaction(null, TransactionType.DEPOT, montant, LocalDateTime.now(), comptePourTransaction));
        return new CompteBancaire(this.id, nouveauSolde, nouvellesTransactions);
    }

    public CompteBancaire retirer(BigDecimal montant) {
        if (this.solde.compareTo(montant) < 0) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        BigDecimal nouveauSolde = this.solde.subtract(montant);
        List<Transaction> nouvellesTransactions = new ArrayList<>(this.transactions);
        CompteBancaire comptePourTransaction = new CompteBancaire(this.id, nouveauSolde, new ArrayList<>());
        nouvellesTransactions.add(
                new Transaction(null, TransactionType.RETRAIT, montant, LocalDateTime.now(), comptePourTransaction));
        return new CompteBancaire(this.id, nouveauSolde, nouvellesTransactions);
    }
}