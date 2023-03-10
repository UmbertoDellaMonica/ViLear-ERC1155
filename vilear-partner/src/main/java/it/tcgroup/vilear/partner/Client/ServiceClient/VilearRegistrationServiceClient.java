package it.tcgroup.vilear.partner.Client.ServiceClient;

import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;

public interface VilearRegistrationServiceClient {

    /**
     * Call at Vilear-Registration
     * Recupera tutte le Candidazioni dei Partner
     * @param page numero pagina
     * @param pageSize dimensione della Pagina
     * GET_METHOD
     */
    PaginatedResponse<PartnerCandidationResponse> getPartnerCandidations(Integer page, Integer pageSize);


    /**
     * Call at Vilear-Registration
     * Recupera se esiste i dati anagrafici della Persona
     * @param personId id della Persona
     * GET_METHOD
     */
    PersonResponse getPerson(Integer personId);

    /**
     * Call at Vilear-Registration
     * Accetta o Rifiuta lo stato della Candidatura
     * @param partnerModel contiene i dati del Partner
     * @param decisionEnum ACCEPT/DENY
     * PUT_METHOD
     */
    PartnerCandidationResponse choose(PartnerModel partnerModel, DecisionEnum decisionEnum);

    /**
     * Call at Vilear-Registration
     * Recupera la singola Partecipazione del Partner
     * @param userId id dell'utente che ha effettuato la Partecipazione
     */
    PartnerCandidationResponse getPartnerCandidation(Integer userId);
}
