package com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity;

import com.exalt.bankaccount.domain.model.TransactionType;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
    private CompteBancaireEntity compteBancaire;
}
