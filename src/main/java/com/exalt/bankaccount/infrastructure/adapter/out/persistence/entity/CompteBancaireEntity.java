package com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compte_bancaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CompteBancaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal solde = BigDecimal.ZERO;

    // IMPORTANT : On ajoute fetch = FetchType.EAGER pour s'assurer que les
    // transactions sont chargées avec le compte
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compteBancaire", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionEntity> transactions = new ArrayList<>();

    // Méthode utilitaire pour garder la cohérence de la relation
    public void addTransaction(TransactionEntity transaction) {
        transactions.add(transaction);
        transaction.setCompteBancaire(this);
    }
}