package it.tcgroup.vilear.registration.Client.FallBack;

import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import it.tcgroup.vilear.registration.Client.VilearAuthClient;
import org.springframework.http.ResponseEntity;

public class VilearAuthClientFallBack implements VilearAuthClient {
    @Override
    public ResponseEntity<UserAuthenticationResponse> verifyToken(String token) {
        return null;
    }

    @Override
    public ResponseEntity<UserAuthenticationResponse> verifyTokenByUser(String username) {
        return null;
    }
}
