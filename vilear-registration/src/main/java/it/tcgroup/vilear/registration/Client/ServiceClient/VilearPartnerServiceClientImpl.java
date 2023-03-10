package it.tcgroup.vilear.registration.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.base.Payload.Request.PartnerRequest;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import it.tcgroup.vilear.registration.Client.VilearPartnerClient;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Model.PartnerModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VilearPartnerServiceClientImpl implements VilearPartnerServiceClient{

    private final VilearPartnerClient vilearPartnerClient;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PartnerResponse addPartner(PartnerCandidationModel partnerCandidationModel){
        try{
            PartnerModel partnerModel = partnerCandidationModel.getPartner();
            // Inserisco i dati di default del Partner
            partnerModel.setUserId(partnerCandidationModel.getUser().getId());
            partnerModel.setAdmin(false);
            partnerModel.setToken(UUID.randomUUID());
            PartnerRequest partnerRequest = mapper.map(partnerModel,PartnerRequest.class);
            // Invio la richiesta
            ResponseEntity<PartnerResponse> partnerResponseResponseEntity = vilearPartnerClient.addPartner(partnerRequest);
            if(partnerResponseResponseEntity==null){
                throw new RuntimeException("EXCEPTION : PARTNER WAS EMPTY");
            }
            return partnerResponseResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION :"+e.getMessage());
        }
    }

}
