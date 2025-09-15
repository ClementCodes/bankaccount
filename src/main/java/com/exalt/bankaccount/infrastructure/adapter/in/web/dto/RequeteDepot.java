package com.exalt.bankaccount.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RequeteDepot {
    @NotNull(message = "Le montant ne peut pas être nul.")
    @Positive(message = "Le montant du dépôt doit être positif.")
    private BigDecimal montant;
}