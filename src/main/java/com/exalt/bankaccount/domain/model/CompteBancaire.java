package com.exalt.bankaccount.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Le constructeur est privé pour forcer l'utilisation des méthodes
@EqualsAndHashCode
public class CompteBancaire {

    private final Long id;
    private final BigDecimal solde;
    private final List<Transaction> transactions;

    // Constructeur public pour la création initiale
    public CompteBancaire() {
        this.id = null;
        this.solde = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    // Méthode qui retourne une NOUVELLE instance de CompteBancaire avec le solde
    // mis à jour
    public CompteBancaire deposer(BigDecimal montant) {
        BigDecimal nouveauSolde = this.solde.add(montant);
        List<Transaction> nouvellesTransactions = new ArrayList<>(this.transactions);
        // On crée une nouvelle instance de CompteBancaire pour la transaction pour
        // éviter les références circulaires
        CompteBancaire comptePourTransaction = new CompteBancaire(this.id, nouveauSolde, new ArrayList<>());
        nouvellesTransactions
                .add(new Transaction(null, TransactionType.DEPOT, montant, LocalDateTime.now(), comptePourTransaction));
        return new CompteBancaire(this.id, nouveauSolde, nouvellesTransactions);
    }

    // Méthode qui retourne une NOUVELLE instance de CompteBancaire avec le solde
    // mis à jour
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