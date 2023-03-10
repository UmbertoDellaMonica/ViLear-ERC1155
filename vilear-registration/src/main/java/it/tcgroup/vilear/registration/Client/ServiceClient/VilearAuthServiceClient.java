package it.tcgroup.vilear.registration.Client.ServiceClient;

import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;

public interface VilearAuthServiceClient {

    /**
     * Verifica il token autenticandolo
     * @param token contiene il token
     */
    UserAuthenticationResponse verifyToken(String token);

    /**
     * Recupera i dati dell'utente
     * @param token contiene i dati del Token
     */
    UserAuthenticationResponse verifyTokenByUsername(String token);


}
