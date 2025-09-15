package com.exalt.bankaccount.application.port.in;

import com.exalt.bankaccount.domain.model.CompteBancaire;

public interface ICasUtilisationSauvegardeCompte {
    CompteBancaire saveAccount(CompteBancaire compte);
}
