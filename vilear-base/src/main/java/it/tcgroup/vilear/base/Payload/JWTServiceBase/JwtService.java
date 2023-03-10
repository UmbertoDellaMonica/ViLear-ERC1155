package it.tcgroup.vilear.base.Payload.JWTServiceBase;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtService {
    String retrieveToken(HttpServletRequest request);

    /**
     * Recupera l'utente dal token
     * @param token JWT
     */
    Authentication extractUserFromToken(String token);

    /**
     * Recupera la lista delle Autorità dal Token JWT
     * @param token JWT
     */
    List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token, Claims claims);

    /**
     * Recupera tutti i Claims dal Token
     * @param token JWT
     */
    Claims getClaimsFromToken(String token);

    /**
     * Elimina il Bearer dal Token
     * @param token
     */
    String noBearerToken(String token);
}
