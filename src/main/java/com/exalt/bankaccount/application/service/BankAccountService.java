package com.exalt.bankaccount.application.service;

import com.exalt.bankaccount.application.port.in.IConsultBalanceUseCase;
import com.exalt.bankaccount.application.port.in.IDepositUseCase;
import com.exalt.bankaccount.application.port.in.ISaveAccountUseCase;
import com.exalt.bankaccount.application.port.in.ITransactionHistoryUseCase;
import com.exalt.bankaccount.application.port.in.IWithdrawUseCase;
import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.domain.CompteBancaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService implements IDepositUseCase, IWithdrawUseCase, IConsultBalanceUseCase, ITransactionHistoryUseCase, ISaveAccountUseCase {

    private final IBankAccountRepositoryPort bankAccountRepositoryPort;
    private final CompteBancaireService compteBancaireService;

    @Override
    public void deposer(Long compteId, BigDecimal montant) {
        CompteBancaire compte = bankAccountRepositoryPort.findById(compteId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable"));
        compteBancaireService.deposer(compte, montant);
        bankAccountRepositoryPort.save(compte);
    }

    @Override
    public void retirer(Long compteId, BigDecimal montant) {
        CompteBancaire compte = bankAccountRepositoryPort.findById(compteId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable"));
        compteBancaireService.retirer(compte, montant);
        bankAccountRepositoryPort.save(compte);
    }

    @Override
    public BigDecimal consulterSolde(Long compteId) {
        return bankAccountRepositoryPort.findById(compteId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable"))
                .getSolde();
    }

    @Override
    public List<Transaction> historiqueTransactions(Long compteId) {
        return bankAccountRepositoryPort.findById(compteId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable"))
                .getTransactions();
    }

    @Override
    public CompteBancaire saveAccount(CompteBancaire compte) {
        return bankAccountRepositoryPort.save(compte);
    }
}