package it.tcgroup.vilear.partner.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Client.VilearRegistrationClient;
import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearRegistrationServiceClientImpl implements VilearRegistrationServiceClient{

    private final VilearRegistrationClient vilearRegistrationClient;

    @Override
    public PaginatedResponse<PartnerCandidationResponse> getPartnerCandidations(Integer page, Integer pageSize) {
        try {
            ResponseEntity<PaginatedResponse<PartnerCandidationResponse>> paginatedResponseResponseEntity =
                    vilearRegistrationClient.getPartnerCandidations(page, pageSize);
            if(paginatedResponseResponseEntity == null){
                throw new RuntimeException("EXCEPTION NULL");
            }

            return paginatedResponseResponseEntity.getBody();
        }catch(FeignException e){
            throw  new RuntimeException("EXCEPTION: "+e.getMessage());
        }
    }

    @Override
    public PartnerCandidationResponse choose(PartnerModel partnerModel, DecisionEnum decisionEnum) {
        try{
            Integer userId = partnerModel.getUserId();
            // Dobbiamo chiamare Registration,
            ResponseEntity<PartnerCandidationResponse> partnerCandidationResponseResponseEntity = vilearRegistrationClient.choose(userId,decisionEnum);
            if(partnerCandidationResponseResponseEntity == null) {
                throw new RuntimeException("Partner Not Avaiable Exception FeignClient");
            }
            return partnerCandidationResponseResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException(" "+ e.getMessage());
        }
    }

    @Override
    public PartnerCandidationResponse getPartnerCandidation(Integer userId) {
        try{
            ResponseEntity<PartnerCandidationResponse>partnerCandidationResponseResponseEntity = vilearRegistrationClient
                    .getPartnerCandidation(userId);
            if(!partnerCandidationResponseResponseEntity.hasBody()){
                throw new RuntimeException("EXCEPTION Partner Candidation Not avaible");
            }
            return partnerCandidationResponseResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException(" "+ e.getMessage());
        }
    }


    @Override
    public PersonResponse getPerson(Integer personId) {
        try{
            ResponseEntity<PersonResponse> personResponse = vilearRegistrationClient.getPerson(personId);
            if(!personResponse.hasBody()){
                throw new RuntimeException("EXCEPTION: PERSON RESPONSE IS NULL");
            }
            return personResponse.getBody();
        }catch(FeignException e){
            throw new RuntimeException(" EXCEPTION "+ e.getMessage());
        }
    }

}
