package it.tcgroup.vilear.registration.Client.ServiceClient;

import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;

public interface VilearPartnerServiceClient {

    /**
     * Call at Vilear-Partner:
     * Salva i dati del Partner
     * @param partnerCandidationModel contiene i dati del PartnerCandidation
     */
    PartnerResponse addPartner(PartnerCandidationModel partnerCandidationModel);

}
