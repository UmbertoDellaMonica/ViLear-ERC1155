package it.tcgroup.vilear.registration.Client.FallBack;

import it.tcgroup.vilear.base.Payload.Request.EmailRequest;
import it.tcgroup.vilear.registration.Client.VilearSenderClient;
import org.springframework.http.ResponseEntity;

public class VilearSenderClientFallBack implements VilearSenderClient {

    @Override
    public ResponseEntity<Boolean> sendInviteEmail(EmailRequest emailRequest) {
        return null;
    }
}
