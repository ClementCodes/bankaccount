package com.exalt.bankaccount.infrastructure.adapter.in.web.dto;

import com.exalt.bankaccount.domain.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private TransactionType type;
    private BigDecimal montant;
    private LocalDateTime date;
}
