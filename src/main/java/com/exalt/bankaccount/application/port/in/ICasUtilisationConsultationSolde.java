package com.exalt.bankaccount.application.port.in;

import java.math.BigDecimal;

public interface ICasUtilisationConsultationSolde {
    BigDecimal consulterSolde(Long compteId);
}
