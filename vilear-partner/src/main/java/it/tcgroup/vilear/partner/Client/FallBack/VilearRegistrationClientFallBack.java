package it.tcgroup.vilear.partner.Client.FallBack;

import it.tcgroup.vilear.partner.Client.VilearRegistrationClient;
import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import org.springframework.http.ResponseEntity;

public class VilearRegistrationClientFallBack implements VilearRegistrationClient {

    @Override
    public ResponseEntity<PartnerCandidationResponse> choose(Integer userId, DecisionEnum decisionEnum) {
        return null;
    }

    @Override
    public ResponseEntity<PaginatedResponse<PartnerCandidationResponse>> getPartnerCandidations(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<PersonResponse> getPerson(Integer personId) {
        return null;
    }

    @Override
    public ResponseEntity<PartnerCandidationResponse> getPartnerCandidation(Integer userId) {
        return null;
    }

}
