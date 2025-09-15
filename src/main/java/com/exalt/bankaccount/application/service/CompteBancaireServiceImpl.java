package com.exalt.bankaccount.application.service;

import com.exalt.bankaccount.application.port.in.*; // Import de toutes les interfaces
import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompteBancaireServiceImpl implements ICasUtilisationDepot, ICasUtilisationRetrait,
		ICasUtilisationConsultationSolde, ICasUtilisationHistoriqueTransactions, ICasUtilisationSauvegardeCompte {

	private final IBankAccountRepositoryPort bankAccountRepositoryPort;

	@Override
	@Transactional
	public void deposer(Long compteId, BigDecimal montant) {
		CompteBancaire compteActuel = findCompteById(compteId);
		CompteBancaire nouveauCompte = compteActuel.deposer(montant); // Appel de la nouvelle méthode du domaine
		bankAccountRepositoryPort.save(nouveauCompte);
	}

	@Override
	@Transactional
	public void retirer(Long compteId, BigDecimal montant) {
		CompteBancaire compteActuel = findCompteById(compteId);
		CompteBancaire nouveauCompte = compteActuel.retirer(montant); // Appel de la nouvelle méthode du domaine
		bankAccountRepositoryPort.save(nouveauCompte);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal consulterSolde(Long compteId) {
		return findCompteById(compteId).getSolde();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transaction> historiqueTransactions(Long compteId) {
		return findCompteById(compteId).getTransactions();
	}

	// On implémente la méthode manquante avec le bon nom
	@Override
	@Transactional
	public CompteBancaire sauvegarderCompte(CompteBancaire compte) {
		return bankAccountRepositoryPort.save(compte);
	}

	private CompteBancaire findCompteById(Long compteId) {
		return bankAccountRepositoryPort.findById(compteId)
				.orElseThrow(() -> new IllegalArgumentException("Compte introuvable pour l'ID : " + compteId));
	}
}