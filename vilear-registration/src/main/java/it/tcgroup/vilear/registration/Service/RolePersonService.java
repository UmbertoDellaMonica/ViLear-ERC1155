package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.registration.Model.RolePersonModel;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface RolePersonService {

    /**
     * Inserimento del Ruolo
     * @param rolePersonModel contiene i dati di persona e ruolo
     * @return un Ruolo per una Persona
     */
    RolePersonModel add(RolePersonModel rolePersonModel);

    /**
     * Recupero un Ruolo della Persona tramite :
     * @param role  ruolo
     * @param personId id della persona
     * @return un Ruolo associato ad un persona
     */
    RolePersonModel getRolePerson(RoleEnum role, Integer personId);

    /**
     * Recupera tutte  le persone che hanno quel ruolo
     * @param role ruolo
     * @return una Lista di Persona con un determinato ruolo
     */
    PaginatedResponse<RolePersonModel> getPersonsByRole(String role, Integer page, Integer pageSize) throws RoleNotFoundException;

    /**
     * Elimina un Ruolo associato ad una persona
     *  @param role id del ruolo
     * @param personId id della persona
     */
    void deleteRolePerson(RoleEnum role,Integer personId);
}
