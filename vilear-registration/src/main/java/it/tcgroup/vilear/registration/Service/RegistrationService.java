package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.registration.Model.RegistrationModel;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

public interface RegistrationService {

    /**
     * Registrazione di un nuovo utente
     * Se quest'ultimo NON risulta associato a un invito (tramite token)
     *      => Viene creata l'utenza
     * Altrimenti, oltre alla creazione dell'utenza, viene anche aggiornato l'invito
     * @param registrationModel si riferisce ai dati dell'utente
     * @param token si riferisce al token d'invito
     * @return RegistrationModel
     */
    RegistrationModel registration(RegistrationModel registrationModel, UUID token) throws RoleNotFoundException;

}
