package com.exalt.bankaccount;

import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.application.service.CompteBancaireServiceImpl;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Active les annotations Mockito
public class BankAccountServiceTest {

    @Mock // Crée un mock pour le port de repository
    private IBankAccountRepositoryPort bankAccountRepositoryPort;

    @InjectMocks // Crée une instance du service et y injecte les mocks
    private CompteBancaireServiceImpl compteBancaireService;

    @Test
    void testDepositer_casNominal() {
        // Arrange (Given)
        CompteBancaire compteInitial = new CompteBancaire(); // Un compte avec solde 0
        when(bankAccountRepositoryPort.findById(1L)).thenReturn(Optional.of(compteInitial));

        // Act (When)
        compteBancaireService.deposer(1L, BigDecimal.valueOf(100));

        // Assert (Then)
        // On vérifie que la méthode save a été appelée
        // On ne peut pas vérifier le solde directement car le modèle est immuable
        // Pour un test plus poussé, on utiliserait un ArgumentCaptor
        verify(bankAccountRepositoryPort, times(1)).save(any(CompteBancaire.class));
    }

    @Test
    void testRetirer_soldeInsuffisant_doitLeverException() {
        // Arrange (Given)
        CompteBancaire compteAvecPeuDeSolde = new CompteBancaire(); // Solde à 0
        when(bankAccountRepositoryPort.findById(1L)).thenReturn(Optional.of(compteAvecPeuDeSolde));

        // Act & Assert (When & Then)
        // On vérifie que l'appel à retirer() lève bien une exception
        assertThrows(IllegalArgumentException.class, () -> {
            compteBancaireService.retirer(1L, BigDecimal.valueOf(100));
        });

        // On vérifie que la méthode save n'a JAMAIS été appelée
        verify(bankAccountRepositoryPort, never()).save(any(CompteBancaire.class));
    }
}