package com.exalt.bankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor // C'est la version correcte, sans accès privé
@EqualsAndHashCode
public class CompteBancaire {

    private final Long id;
    private final BigDecimal solde;
    private final List<Transaction> transactions;

    /**
     * Constructeur public utilisé uniquement pour la création initiale d'un compte
     * qui n'est pas encore en base de données.
     */
    public CompteBancaire() {
        this.id = null;
        this.solde = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    /**
     * Crée et retourne une nouvelle instance de CompteBancaire représentant l'état
     * après un dépôt.
     * 
     * @param montant Le montant à déposer.
     * @return Un nouvel objet CompteBancaire avec le solde et l'historique mis à
     *         jour.
     */
    public CompteBancaire deposer(BigDecimal montant) {
        BigDecimal nouveauSolde = this.solde.add(montant);
        List<Transaction> nouvellesTransactions = new ArrayList<>(this.transactions);

        CompteBancaire comptePourTransaction = new CompteBancaire(this.id, nouveauSolde, new ArrayList<>());
        nouvellesTransactions
                .add(new Transaction(null, TransactionType.DEPOT, montant, LocalDateTime.now(), comptePourTransaction));

        return new CompteBancaire(this.id, nouveauSolde, nouvellesTransactions);
    }

    /**
     * Crée et retourne une nouvelle instance de CompteBancaire représentant l'état
     * après un retrait.
     * 
     * @param montant Le montant à retirer.
     * @return Un nouvel objet CompteBancaire avec le solde et l'historique mis à
     *         jour.
     * @throws IllegalArgumentException si le solde est insuffisant.
     */
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