package com.exalt.bankaccount.infrastructure.adapter.out.persistence;

import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.mapper.CompteBancaireMapper;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.repository.JpaBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BankAccountRepositoryImpl implements IBankAccountRepositoryPort {

    private final JpaBankAccountRepository jpaBankAccountRepository;
    private final CompteBancaireMapper mapper;

    @Override
    public Optional<CompteBancaire> findById(Long id) {
        return jpaBankAccountRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public CompteBancaire save(CompteBancaire compteBancaire) {
        CompteBancaireEntity entity = mapper.toEntity(compteBancaire);
        return mapper.toDomain(jpaBankAccountRepository.save(entity));
    }
}