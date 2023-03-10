package it.tcgroup.vilear.auth.Client.ServiceClient;
import feign.FeignException;
import it.tcgroup.vilear.auth.Client.VilearPartnerClient;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearPartnerServiceClientImpl implements VilearPartnerServiceClient {

    private final VilearPartnerClient vilearPartnerClient;

    @Override
    public PartnerResponse getPartnerByUser(Integer userId) {
        try{
            // Invio la richiesta
            ResponseEntity<PartnerResponse> partnerResponseResponseEntity = vilearPartnerClient.getPartnerByUser(userId);
            if(partnerResponseResponseEntity.getBody() == null){
                return null;
            }
            return partnerResponseResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION :"+e.getMessage());
        }
    }
}
