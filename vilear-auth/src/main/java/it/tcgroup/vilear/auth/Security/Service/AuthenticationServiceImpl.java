package it.tcgroup.vilear.auth.Security.Service;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private JwtServiceAuth jwtServiceAuth;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void setAuthentication(String token) {
        Authentication authentication = jwtServiceAuth.extractUserFromToken(token);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    @Override
    public void newAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Effettua una verifica
        checkUsernameAndPassword(request);
        // Recupera i campi dello username e Password
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Esegue l'autenticazione del Personaggio
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return;
    }

    @Override
    public void checkUsernameAndPassword(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || password == null){
            throw new Unahautorized();
        }
    }

    @Override
    public Boolean checkToken(HttpServletRequest request) {
        // Se questo token esiste allora posso effettuare direttamente l'autenticazione
        String token = jwtServiceAuth.retrieveToken(request);
        if(token!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
