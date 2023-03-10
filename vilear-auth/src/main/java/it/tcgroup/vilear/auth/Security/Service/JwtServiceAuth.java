package it.tcgroup.vilear.auth.Security.Service;

import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import org.springframework.security.core.Authentication;

public interface JwtServiceAuth extends JwtService {
    /**
     * Costruisce il Token
     * @param autentication l'autenticazione
     * @param username      l'autenticazione
     */
    String buildToken(Authentication autentication, String username);

    /**
     * Verifica se un Token è Expired
     * @param token JWT
     */
    Boolean isExpiredToken(String token);

}
