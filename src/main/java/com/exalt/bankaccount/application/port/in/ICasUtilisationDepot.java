package com.exalt.bankaccount.application.port.in;

import java.math.BigDecimal;

public interface ICasUtilisationDepot {
    void deposer(Long compteId, BigDecimal montant);
}
