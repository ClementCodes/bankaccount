package com.exalt.bankaccount.application.port.in;

import com.exalt.bankaccount.domain.model.CompteBancaire;

public interface ICasUtilisationSauvegardeCompte {
    // Le nom de la méthode doit être celui-ci
    CompteBancaire sauvegarderCompte(CompteBancaire compte);
}