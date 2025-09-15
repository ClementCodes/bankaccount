package com.exalt.bankaccount.application.port.in;

import java.math.BigDecimal;

public interface ICasUtilisationRetrait {
    void retirer(Long compteId, BigDecimal montant);
}
