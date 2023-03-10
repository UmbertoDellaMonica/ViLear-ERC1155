package it.tcgroup.vilear.course.Security.Filter;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearAuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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


public class JwtCheckTokenFilter extends OncePerRequestFilter {

    @Autowired
    private VilearAuthServiceClient vilearAuthServiceClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String pass = "pass";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
            // Recupero il Token
            // Recupera l'autenticazione
        if(
                request.getRequestURI().equalsIgnoreCase("/external/tool")||
                request.getRequestURI().equalsIgnoreCase("/external/teacher")||
                request.getRequestURI().equalsIgnoreCase("/external/logistic")
        ){
            filterChain.doFilter(request,response);
            return;
        }
            Authentication authentication = retrieveDataAuthentication(request.getHeader(HttpHeaders.AUTHORIZATION));
            if(authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request,response);
            return;
    }

    private Authentication retrieveDataAuthentication(String token) {
        // Recupera il token
        if (token != null) {
            token = token.replaceFirst("Bearer ", "");
            // Devo recuperare i dati dell'autenticazione
            UserAuthenticationResponse userAuthenticationResponse = vilearAuthServiceClient.verifyToken(token);
            String username = userAuthenticationResponse.getEmail();
            String password = userAuthenticationResponse.getPassword();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username,pass);
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authentication;
        }
        throw new Unahautorized();
    }
}