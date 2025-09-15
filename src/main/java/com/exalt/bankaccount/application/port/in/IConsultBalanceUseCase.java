package com.exalt.bankaccount.application.port.in;

import java.math.BigDecimal;

public interface IConsultBalanceUseCase {
    BigDecimal consulterSolde(Long compteId);
}
