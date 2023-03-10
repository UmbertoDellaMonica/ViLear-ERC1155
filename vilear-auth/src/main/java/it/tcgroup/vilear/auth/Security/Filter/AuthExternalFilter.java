package it.tcgroup.vilear.auth.Security.Filter;

import io.jsonwebtoken.ExpiredJwtException;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthExternalFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServiceAuth jwtService;

    @Autowired
    private UserRedisService userRedisService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if(
                request.getRequestURI().equals("/auth/verify") ||
                request.getRequestURI().equals("/auth")
        ) {
            String token = request.getHeader("token");
            try {
                if(checkToken(token)){
                // Recupero l'autenticazione
                    Authentication authentication = jwtService.extractUserFromToken(token);
                // Esegue un'autenticazione
                    if (authentication.isAuthenticated()) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    filterChain.doFilter(request, response);
                    return;
                }
                throw new Unahautorized();
            } catch(ExpiredJwtException e){
                // Elimino l'utenza se questa esiste
                deleteTokenFromCache(token);
            }

        }
        filterChain.doFilter(request,response);
        return;
    }

    private void deleteTokenFromCache(String token) {
        UserJwtModel userJwtModel = userRedisService.getByToken(token);
        if(userJwtModel!=null) {
            userRedisService.deleteUser(userJwtModel);
            return;
        }else{
            throw new Unahautorized();
        }
    }

    private Boolean checkToken(String token) {
        // Se questo token esiste allora posso effettuare direttamente l'autenticazione
        if(token!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
