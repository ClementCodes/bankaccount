package com.exalt.bankaccount.infrastructure.adapter.in.web.mapper;

import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.ReponseCompte;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.ReponseTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteBancaireDtoMapper {

    ReponseCompte toDto(CompteBancaire compteBancaire);

    ReponseTransaction toDto(Transaction transaction);
}