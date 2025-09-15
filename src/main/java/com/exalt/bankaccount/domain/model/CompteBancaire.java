package com.exalt.bankaccount.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CompteBancaire {

    private Long id;
    private BigDecimal solde = BigDecimal.ZERO;
    private List<Transaction> transactions = new ArrayList<>();

    
}
