package it.tcgroup.vilear.auth.Client.ServiceClient;

import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;

public interface VilearPartnerServiceClient {
    /**
     * Call at Vilear-Partner:
     * Recupera il Partner
     * @param userId id del Partner
     */
    PartnerResponse getPartnerByUser(Integer userId);
}
