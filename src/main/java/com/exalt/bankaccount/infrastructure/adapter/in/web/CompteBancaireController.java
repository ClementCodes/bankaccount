package com.exalt.bankaccount.infrastructure.adapter.in.web;

import com.exalt.bankaccount.application.port.in.ICasUtilisationConsultationSolde;
import com.exalt.bankaccount.application.port.in.ICasUtilisationDepot;
import com.exalt.bankaccount.application.port.in.ICasUtilisationHistoriqueTransactions;
import com.exalt.bankaccount.application.port.in.ICasUtilisationRetrait;
import com.exalt.bankaccount.application.port.in.ICasUtilisationSauvegardeCompte;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.ReponseCompte;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.ReponseTransaction;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.RequeteDepot;
import com.exalt.bankaccount.infrastructure.adapter.in.web.dto.RequeteRetrait;
import com.exalt.bankaccount.infrastructure.adapter.in.web.mapper.CompteBancaireDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comptes-bancaires")
@RequiredArgsConstructor
public class CompteBancaireController {

    private final ICasUtilisationDepot casUtilisationDepot;
    private final ICasUtilisationRetrait casUtilisationRetrait;
    private final ICasUtilisationConsultationSolde casUtilisationConsultationSolde;
    private final ICasUtilisationHistoriqueTransactions casUtilisationHistoriqueTransactions;
    private final ICasUtilisationSauvegardeCompte casUtilisationSauvegardeCompte;
    private final CompteBancaireDtoMapper mapper;

    @PostMapping
    public ResponseEntity<ReponseCompte> creerCompte() {
        CompteBancaire compte = new CompteBancaire();
        compte = casUtilisationSauvegardeCompte.sauvegarderCompte(compte);
        return new ResponseEntity<>(mapper.toDto(compte), HttpStatus.CREATED);
    }

    @PatchMapping("/{compteId}/depot")
    public ResponseEntity<Void> deposer(@PathVariable Long compteId, @Valid @RequestBody RequeteDepot requete) {
        casUtilisationDepot.deposer(compteId, requete.getMontant());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{compteId}/retrait")
    public ResponseEntity<Void> retirer(@PathVariable Long compteId, @Valid @RequestBody RequeteRetrait requete) {
        casUtilisationRetrait.retirer(compteId, requete.getMontant());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{compteId}/solde")
    public ResponseEntity<BigDecimal> consulterSolde(@PathVariable Long compteId) {
        BigDecimal solde = casUtilisationConsultationSolde.consulterSolde(compteId);
        return ResponseEntity.ok(solde);
    }

    @GetMapping("/{compteId}/transactions")
    public ResponseEntity<List<ReponseTransaction>> historiqueTransactions(@PathVariable Long compteId) {
        List<ReponseTransaction> transactions = casUtilisationHistoriqueTransactions.historiqueTransactions(compteId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }
}