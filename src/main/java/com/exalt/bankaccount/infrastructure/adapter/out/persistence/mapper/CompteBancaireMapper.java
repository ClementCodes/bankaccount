package com.exalt.bankaccount.infrastructure.adapter.out.persistence.mapper;

import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompteBancaireMapper {

    CompteBancaire toDomain(CompteBancaireEntity entity);

    CompteBancaireEntity toEntity(CompteBancaire domain);

    @Mapping(target = "compteBancaire", ignore = true)
    Transaction toDomain(TransactionEntity entity);

    TransactionEntity toEntity(Transaction domain);
}
