package com.exalt.bankaccount.infrastructure.adapter.in.web.mapper;

import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.AccountResponse;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountDtoMapper {

    AccountResponse toDto(CompteBancaire compteBancaire);

    TransactionResponse toDto(Transaction transaction);
}