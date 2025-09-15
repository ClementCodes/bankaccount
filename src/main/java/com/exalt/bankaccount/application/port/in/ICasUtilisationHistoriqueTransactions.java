package com.exalt.bankaccount.application.port.in;

import com.exalt.bankaccount.domain.model.Transaction;

import java.util.List;

public interface ICasUtilisationHistoriqueTransactions {
    List<Transaction> historiqueTransactions(Long compteId);
}
