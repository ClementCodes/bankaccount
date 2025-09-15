package com.exalt.bankaccount;

import com.exalt.bankaccount.application.service.BankAccountService;
import com.exalt.bankaccount.domain.model.CompteBancaire;
import com.exalt.bankaccount.application.port.out.IBankAccountRepositoryPort;
import com.exalt.bankaccount.domain.CompteBancaireService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankAccountServiceTest {

    private final IBankAccountRepositoryPort repository = Mockito.mock(IBankAccountRepositoryPort.class);
    private final CompteBancaireService compteBancaireService = new CompteBancaireService();
    private final BankAccountService service = new BankAccountService(repository, compteBancaireService);

    @Mock
    private IBankAccountRepositoryPort repository;

    @Mock
    private CompteBancaireService compteBancaireService;

    @InjectMocks
    private BankAccountService service;

    @Test
    void testDepositer() {
        CompteBancaire compte = new CompteBancaire();
        compte.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(compte));

        service.deposer(1L, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), compte.getSolde());
        verify(repository, times(1)).save(compte);
    }

    @Test
    void retirer_shouldThrowException_whenCompteNotFound() {
        // Arrange (Given)
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert (When & Then)
        assertThrows(IllegalArgumentException.class, () -> {
            service.retirer(1L, BigDecimal.valueOf(100));
        });
    }
    // Ajoute d'autres tests pour retirer, consulterSolde, etc.
}
