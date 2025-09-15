package com.exalt.bankaccount.infrastructure.adapter.out.persistence.repository;

import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankAccountRepository extends JpaRepository<CompteBancaireEntity, Long> {
}
