package com.exalt.bankaccount.application.service;

import com.exalt.bankaccount.application.port.in.ICasUtilisationConsultationSolde;
import com.exalt.bankaccount.application.port.in.ICasUtilisationDepot;
import com.exalt.bankaccount.application.port.in.ICasUtilisationSauvegardeCompte;
import com.exalt.bankaccount.application.port.in.ICasUtilisationHistoriqueTransactions;
import com.exalt.bankaccount.application.port.in.ICasUtilisationRetrait;
import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Important pour les transactions

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
// On renomme la classe pour qu'elle corresponde aux cas d'utilisation qu'elle
// implémente
public class CompteBancaireServiceImpl implements ICasUtilisationDepot, ICasUtilisationRetrait,
		ICasUtilisationConsultationSolde, ICasUtilisationHistoriqueTransactions, ICasUtilisationSauvegardeCompte {

	private final IBankAccountRepositoryPort bankAccountRepositoryPort;

	@Override
	@Transactional // Assure que la lecture et la sauvegarde se font dans une seule transaction
	public void deposer(Long compteId, BigDecimal montant) {
		// 1. On charge le compte
		CompteBancaire compteActuel = findCompteById(compteId);

		// 2. On appelle la logique du domaine, qui retourne une NOUVELLE instance
		CompteBancaire nouveauCompte = compteActuel.deposer(montant); // Supposant que cette méthode existe dans le
																		// domaine immuable

		// 3. On sauvegarde la nouvelle instance
		bankAccountRepositoryPort.save(nouveauCompte);
	}

	@Override
	@Transactional
	public void retirer(Long compteId, BigDecimal montant) {
		// 1. On charge le compte
		CompteBancaire compteActuel = findCompteById(compteId);

		// 2. On appelle la logique du domaine, qui retourne une NOUVELLE instance
		CompteBancaire nouveauCompte = compteActuel.retirer(montant); // Supposant que cette méthode existe dans le
																		// domaine immuable

		// 3. On sauvegarde la nouvelle instance
		bankAccountRepositoryPort.save(nouveauCompte);
	}

	@Override
	@Transactional(readOnly = true) // Optimisation pour les opérations de lecture seule
	public BigDecimal consulterSolde(Long compteId) {
		return findCompteById(compteId).getSolde();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transaction> historiqueTransactions(Long compteId) {
		return findCompteById(compteId).getTransactions();
	}

	@Override
	@Transactional
	public CompteBancaire sauvegarderCompte(CompteBancaire compte) {
		return bankAccountRepositoryPort.save(compte);
	}

	/**
	 * Méthode privée pour éviter la duplication de code pour la recherche de
	 * compte.
	 * C'est une bonne pratique (DRY - Don't Repeat Yourself).
	 */
	private CompteBancaire findCompteById(Long compteId) {
		return bankAccountRepositoryPort.findById(compteId)
				.orElseThrow(() -> new IllegalArgumentException("Compte introuvable pour l'ID : " + compteId)); // Remplacer
																												// par
																												// une
																												// exception
																												// plus
																												// spécifique
																												// si
																												// besoin
	}
}