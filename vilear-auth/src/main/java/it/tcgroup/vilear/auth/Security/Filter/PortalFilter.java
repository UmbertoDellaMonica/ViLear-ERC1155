package it.tcgroup.vilear.auth.Security.Filter;

import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PortalFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServiceAuth jwtService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if(
                request.getRequestURI().equals("/login") ||
                request.getRequestURI().equals("/logout") ||
                request.getRequestURI().equals("/auth") ||
                request.getRequestURI().equals("/auth/verify") ||
                request.getRequestURI().equals("/auth/verify/manager")
        ){
            filterChain.doFilter(request,response);
            return;
        }
    }
}
