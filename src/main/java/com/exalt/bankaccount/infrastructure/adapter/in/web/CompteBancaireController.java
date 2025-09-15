package com.exalt.bankaccount.infrastructure.adapter.in.web;

import com.exalt.bankaccount.application.port.in.ICasUtilisationConsultationSolde;
import com.exalt.bankaccount.application.port.in.ICasUtilisationDepot;
import com.exalt.bankaccount.application.port.in.ICasUtilisationSauvegardeCompte;
import com.exalt.bankaccount.application.port.in.ICasUtilisationHistoriqueTransactions;
import com.exalt.bankaccount.application.port.in.ICasUtilisationRetrait;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.AccountResponse;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.DepositRequest;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.WithdrawRequest;
import com.exalt.bankaccount.infrastructure.adapter.in.web.mapper.BankAccountDtoMapper;
import jakarta.validation.Valid; // Nécessaire pour la validation
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comptes-bancaires") // URI en français et au pluriel, une convention REST commune
@RequiredArgsConstructor
public class CompteBancaireController { // Nom de classe en français pour la cohérence

    // Injection des ports d'entrée avec les nouveaux noms en français
    private final ICasUtilisationDepot casUtilisationDepot;
    private final ICasUtilisationRetrait casUtilisationRetrait;
    private final ICasUtilisationConsultationSolde casUtilisationConsultationSolde;
    private final ICasUtilisationHistoriqueTransactions casUtilisationHistoriqueTransactions;
    private final ICasUtilisationSauvegardeCompte casUtilisationSauvegardeCompte;
    private final BankAccountDtoMapper mapper;

    @PostMapping
    public ResponseEntity<AccountResponse> creerCompte() {
        CompteBancaire compte = new CompteBancaire();
        compte = casUtilisationSauvegardeCompte.sauvegarderCompte(compte);
        return new ResponseEntity<>(mapper.toDto(compte), HttpStatus.CREATED);
    }

    @PatchMapping("/{compteId}/depot") // Utilisation de PATCH pour une mise à jour partielle
    public ResponseEntity<Void> deposer(@PathVariable Long compteId, @Valid @RequestBody DepositRequest request) {
        // L'annotation @Valid déclenche la validation sur le DTO
        casUtilisationDepot.deposer(compteId, request.getMontant());
        return ResponseEntity.noContent().build(); // Retourne un statut 204 No Content, clair pour le client
    }

    @PatchMapping("/{compteId}/retrait") // Utilisation de PATCH
    public ResponseEntity<Void> retirer(@PathVariable Long compteId, @Valid @RequestBody WithdrawRequest request) {
        casUtilisationRetrait.retirer(compteId, request.getMontant());
        return ResponseEntity.noContent().build(); // Retourne un statut 204 No Content
    }

    @GetMapping("/{compteId}/solde")
    public ResponseEntity<BigDecimal> consulterSolde(@PathVariable Long compteId) {
        BigDecimal solde = casUtilisationConsultationSolde.consulterSolde(compteId);
        return ResponseEntity.ok(solde); // Enveloppe la réponse dans un ResponseEntity pour la cohérence
    }

    @GetMapping("/{compteId}/transactions")
    public ResponseEntity<List<TransactionResponse>> historiqueTransactions(@PathVariable Long compteId) {
        List<TransactionResponse> transactions = casUtilisationHistoriqueTransactions.historiqueTransactions(compteId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }
}