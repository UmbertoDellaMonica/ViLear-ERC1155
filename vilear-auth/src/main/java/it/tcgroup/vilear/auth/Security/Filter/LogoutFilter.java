package it.tcgroup.vilear.auth.Security.Filter;

import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutFilter extends OncePerRequestFilter {

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
        if(request.getRequestURI().equals("/logout")) {
            // Devo effettuare il Logout
            // Verifico se esiste il Token
            if(checkToken(request)){
                filterChain.doFilter(request,response);
                return;
            }
        }
        filterChain.doFilter(request, response);
        return;
    }

    private Boolean checkToken(HttpServletRequest request) {
        // Se questo token esiste allora posso effettuare direttamente l'autenticazione
        String token = jwtService.retrieveToken(request);
        if(token!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
