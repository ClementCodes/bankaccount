package com.exalt.bankaccount.infrastructure.adapter.out.persistence.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.TransactionEntity;

@Mapper(componentModel = "spring")
public abstract class CompteBancairePersistenceMapper {

  @ObjectFactory
  protected CompteBancaire newCompteBancaire(CompteBancaireEntity entity) {
    if (entity == null) return null;
    List<Transaction> txs = transactionEntityListToTransactionList(entity.getTransactions());
    // Le constructeur rend la liste immuable dans le domaine, et c'est OK
    return new CompteBancaire(entity.getId(), entity.getSolde(), txs);
  }

  // Important: ignorer transactions ici pour éviter l’addAll
  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "transactions", ignore = true)
  public abstract CompteBancaire toDomain(CompteBancaireEntity entity);

  @Mapping(target = "transactions", ignore = true)
  public abstract CompteBancaireEntity toEntity(CompteBancaire domain);

  @Mapping(target = "compteBancaire", ignore = true)
  public abstract Transaction toDomain(TransactionEntity entity);

  public abstract TransactionEntity toEntity(Transaction domain);

  protected List<Transaction> transactionEntityListToTransactionList(List<TransactionEntity> list) {
    if (list == null || list.isEmpty()) return List.of();
    return list.stream().map(this::toDomain).toList();
  }

  @AfterMapping
  protected void linkTransactions(@MappingTarget CompteBancaireEntity compteEntity, CompteBancaire compteDomain) {
    List<Transaction> txs = compteDomain.getTransactions();
    if (txs == null || txs.isEmpty()) return;
    txs.stream().map(this::toEntity).forEach(compteEntity::addTransaction);
  }
}
