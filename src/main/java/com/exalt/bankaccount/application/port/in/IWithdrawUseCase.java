package com.exalt.bankaccount.application.port.in;

import java.math.BigDecimal;

public interface IWithdrawUseCase {
    void retirer(Long compteId, BigDecimal montant);
}
