package com.exalt.bankaccount.infrastructure.adapter.out.persistence;

import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.entity.CompteBancaireEntity;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.mapper.CompteBancairePersistenceMapper;
import com.exalt.bankaccount.infrastructure.adapter.out.persistence.repository.JpaBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BankAccountRepositoryImpl implements IBankAccountRepositoryPort {

    private final JpaBankAccountRepository jpaBankAccountRepository;
    private final CompteBancairePersistenceMapper mapper;

    @Override
    public Optional<CompteBancaire> findById(Long id) {
        return jpaBankAccountRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * C'est la version corrigée et finale de la méthode save.
     */
    @Override
    public CompteBancaire save(CompteBancaire compteBancaire) {
        // 1. On convertit le domaine en entité
        CompteBancaireEntity entity = mapper.toEntity(compteBancaire);

        // 2. On sauvegarde l'entité. L'instance 'savedEntity' contient maintenant l'ID
        // généré par la base de données.
        CompteBancaireEntity savedEntity = jpaBankAccountRepository.save(entity);

        // 3. On reconvertit l'entité qui a maintenant un ID en objet du domaine.
        // C'est cette étape qui garantit que l'ID n'est pas perdu.
        return mapper.toDomain(savedEntity);
    }
}