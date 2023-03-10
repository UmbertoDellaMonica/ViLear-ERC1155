package it.tcgroup.vilear.auth.Security.Filter;

import io.jsonwebtoken.ExpiredJwtException;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.AuthenticationService;
import it.tcgroup.vilear.auth.Security.Service.FilterService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private FilterService filterService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login")) {
            // Devo verificare:
            // 1) Username And Password siano non Nulli e nel Caso autenticarli
            // 2) Token che sia Non nullo , che esista , che non sia scaduto
            if(authenticationService.checkToken(request)){
                // Esiste un Token
                // Effettuo il Login Tramite il token
                filterService.loginByToken(request,response,filterChain);
                filterChain.doFilter(request,response);
                return;
            }
            // Effettuo una nuova autenticazione se non esiste il token
            authenticationService.newAuthentication(request,response);
            filterChain.doFilter(request, response);
            return;
        }
        // Se non è la request di Login si va avanti
        filterChain.doFilter(request,response);
        return;
    }

}
