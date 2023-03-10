package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import org.springframework.data.domain.Page;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

public interface UserInvitationService {

    /**
     * Invia un'invito all'utente
     * @param userInvitationModel contiene i dati dell'Invito
     * @return un Invito
     */
    UserInvitationModel invite(UserInvitationModel userInvitationModel) throws RoleNotFoundException;

    /**
     * Inserisce i dati dell'invito
     * @param userInvitationModel contiene i dati dell'Invito
     * @return un Invito
     */
    UserInvitationModel add(UserInvitationModel userInvitationModel);

    /**
     * Recupera un'Invito tramite l'id
     * @param userInvitationId id dell'invito
     * @return un Singolo Invito
     */
    UserInvitationModel get(Integer userInvitationId);

    /**
     * Recupera un'Invito tramite il token univoco
     * @param token univoco
     * @return un Singolo Invito
     */
    UserInvitationModel getByToken(UUID token);

    /**
     * Recupera tutti gli inviti
     * @param page pagina
     * @param pageSize numero di elementi all'interno della pagina
     * @return una Lista di Inviti
     */
    PaginatedResponse<UserInvitationModel> filter(Integer page, Integer pageSize);

}
