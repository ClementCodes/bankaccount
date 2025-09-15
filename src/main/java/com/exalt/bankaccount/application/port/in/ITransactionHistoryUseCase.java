package com.exalt.bankaccount.application.port.in;

import com.exalt.bankaccount.domain.model.Transaction;

import java.util.List;

public interface ITransactionHistoryUseCase {
    List<Transaction> historiqueTransactions(Long compteId);
}
