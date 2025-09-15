package com.exalt.bankaccount.infrastructure.adapter.in.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private BigDecimal solde;
}
