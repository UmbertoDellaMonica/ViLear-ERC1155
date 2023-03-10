package it.tcgroup.vilear.registration.Client.ServiceClient;

import it.tcgroup.vilear.registration.Model.UserInvitationModel;

public interface VilearSenderServiceClient {

    /**
     * Invia l'email in relazione al Invito
     * @param userInvitationModel dati dell'invito
     * @return TRUE se l'ha Inviato,FALSE altrimenti
     */
    Boolean sendInviteEmail(UserInvitationModel userInvitationModel);

}
