package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Model.RoleModel;

import javax.management.relation.RoleNotFoundException;

public interface RoleService {

    /**
     * Recupera un Ruolo tramite :
     * @param role ruolo
     * @return un Ruolo
     */
    RoleModel getRoleByLabel(String role) throws RoleNotFoundException;

    /**
     * Recupera un Ruolo tramite :
     * @param roleEnum codice ruolo
     * @return un Ruolo
     */
    RoleModel get(RoleEnum roleEnum) throws RoleNotFoundException;

}