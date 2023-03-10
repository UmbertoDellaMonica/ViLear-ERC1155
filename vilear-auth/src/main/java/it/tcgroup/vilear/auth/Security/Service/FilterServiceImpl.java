package it.tcgroup.vilear.auth.Security.Service;

import io.jsonwebtoken.ExpiredJwtException;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FilterServiceImpl implements FilterService{

    @Autowired
    private JwtServiceAuth jwtServiceAuth;



    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRedisService userRedisService;


    /**
     * Elimina l'utenza in cache se questa esiste
     * Altrimenti esegue una nuova autenticazione
     * @param token JWT token
     * @param request
     * @param response
     */
    @Override
    public void deleteTokenFromCache(
            String token,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Recupero il Token che è Scaduto
        // Lo elimino
        UserJwtModel userJwtModel = userRedisService.getByToken(token);
        if(userJwtModel!=null){
            // Elimino l'utente
            userRedisService.deleteUser(userJwtModel);
            // Eseguo una nuova autenticazione
            authenticationService.newAuthentication(
                    request,
                    response
            );
        }
        // Effettua una nuova autenticazione
        authenticationService.newAuthentication(
                request,
                response
        );
    }

    @Override
    public void loginByToken(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        String token = jwtServiceAuth.retrieveToken(request);
        try {
            // Sto inserendo una autenticazione precedente
            // Estrae i dati dall'autenticazione e convalida il Token
            // Se questo è autenticato va avanti
            // Altimenti se è scaduto il Token allora si passa all'eliminazione
            authenticationService.setAuthentication(token);
        }catch(ExpiredJwtException e){
            // Elimino la vecchia utenza e ne creo una nuova
            // Setto la parte di ExpiredToken
            deleteTokenFromCache(token,request,response);
            return;
        }
    }
}
