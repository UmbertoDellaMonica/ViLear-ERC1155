package it.tcgroup.vilear.registration.Client.FallBack;

import it.tcgroup.vilear.base.Payload.Request.PartnerRequest;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import it.tcgroup.vilear.registration.Client.VilearPartnerClient;
import org.springframework.http.ResponseEntity;

public class VilearPartnerClientFallBack implements VilearPartnerClient {

    @Override
    public ResponseEntity<PartnerResponse> addPartner(PartnerRequest partnerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<PartnerResponse> getPartnerByUser(Integer userId) {
        return null;
    }
}
