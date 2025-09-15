package com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.exalt.bankaccount.domain.model.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
// Si "transaction" est un mot réservé dans votre SGBD, utilisez plutôt:
// @Table(name = "\"transaction\"")
@Table(name = "transaction")
@Data // génère getters, setters, toString, equals, hashCode, requiredArgsConstructor
@NoArgsConstructor // requis par JPA
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal montant;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    @ToString.Exclude // évite chargements paresseux/récursion dans toString
    private CompteBancaireEntity compteBancaire;

    // Éviter les problèmes d'identité: equals/hashCode basés sur l'id uniquement
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity that)) return false;
        // Deux entités sans id (null) ne sont pas égales
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        // HashCode stable pour entités non persistées
        return id != null ? id.hashCode() : 0;
    }
}
