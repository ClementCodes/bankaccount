package com.exalt.bankaccount.application.port.out;

import com.exalt.bankaccount.domain.model.CompteBancaire;

import java.util.Optional;

public interface IBankAccountRepositoryPort {
    Optional<CompteBancaire> findById(Long id);
    CompteBancaire save(CompteBancaire compteBancaire);
}
