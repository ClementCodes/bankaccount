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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compteBancaire", orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();
}
