package com.exalt.bankaccount.infrastructure.adapter.out.persistence.mapper;

import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
// Assurez-vous que le nom de l'interface ici est bien le bon !
public interface CompteBancairePersistenceMapper {

    CompteBancaire toDomain(CompteBancaireEntity entity);

    @Mapping(target = "transactions", ignore = true)
    CompteBancaireEntity toEntity(CompteBancaire domain);

    @Mapping(target = "compteBancaire", ignore = true)
    Transaction toDomain(TransactionEntity entity);

    TransactionEntity toEntity(Transaction domain);

    @AfterMapping
    default void linkTransactions(@MappingTarget CompteBancaireEntity compteEntity, CompteBancaire compteDomain) {
        compteDomain.getTransactions().stream()
                .map(this::toEntity)
                .forEach(compteEntity::addTransaction);
    }
}