package it.tcgroup.vilear.registration.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.base.Payload.Request.EmailRequest;
import it.tcgroup.vilear.registration.Client.VilearSenderClient;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearSenderServiceClientImpl implements VilearSenderServiceClient{
    private final VilearSenderClient vilearSenderClient;

    @Override
    public Boolean sendInviteEmail(UserInvitationModel userInvitationModel) {
        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(userInvitationModel.getEmail());
            emailRequest.setSubject(" Dear User, Sign Up On ViLear! ");
            if(vilearSenderClient.sendInviteEmail(emailRequest).getBody().equals(Boolean.FALSE)){
                throw new RuntimeException(" EMAIL NOT SENDING CORRECTLY ");
            }
            return Boolean.TRUE;
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION : "+e.getMessage());
        }
    }
}
