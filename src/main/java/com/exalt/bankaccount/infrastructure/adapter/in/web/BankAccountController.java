package com.exalt.bankaccount.infrastructure.adapter.in.web;

import com.exalt.bankaccount.application.port.in.IConsultBalanceUseCase;
import com.exalt.bankaccount.application.port.in.IDepositUseCase;
import com.exalt.bankaccount.application.port.in.ISaveAccountUseCase;
import com.exalt.bankaccount.application.port.in.ITransactionHistoryUseCase;
import com.exalt.bankaccount.application.port.in.IWithdrawUseCase;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.AccountResponse;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.DepositRequest;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.WithdrawRequest;
import com.exalt.bankaccount.infrastructure.adapter.in.web.mapper.BankAccountDtoMapper;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bankaccounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final IDepositUseCase depositUseCase;
    private final IWithdrawUseCase withdrawUseCase;
    private final IConsultBalanceUseCase consultBalanceUseCase;
    private final ITransactionHistoryUseCase transactionHistoryUseCase;
    private final ISaveAccountUseCase saveAccountUseCase;
    private final BankAccountDtoMapper mapper;

    @PostMapping("/create")
    public AccountResponse createAccount() {
        CompteBancaire compte = new CompteBancaire();
        compte = saveAccountUseCase.saveAccount(compte);
        return mapper.toDto(compte);
    }

    @PostMapping("/{compteId}/deposer")
    public void deposer(@PathVariable Long compteId, @RequestBody DepositRequest request) {
        depositUseCase.deposer(compteId, request.getMontant());
    }

    @PostMapping("/{compteId}/retirer")
    public void retirer(@PathVariable Long compteId, @RequestBody WithdrawRequest request) {
        withdrawUseCase.retirer(compteId, request.getMontant());
    }

    @GetMapping("/{compteId}/solde")
    public BigDecimal consulterSolde(@PathVariable Long compteId) {
        return consultBalanceUseCase.consulterSolde(compteId);
    }

    @GetMapping("/{compteId}/transactions")
    public List<TransactionResponse> historiqueTransactions(@PathVariable Long compteId) {
        return transactionHistoryUseCase.historiqueTransactions(compteId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}