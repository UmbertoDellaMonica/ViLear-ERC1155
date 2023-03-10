package it.tcgroup.vilear.auth.Security.Service;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FilterService {





    void deleteTokenFromCache(
            String token,
            HttpServletRequest request,
            HttpServletResponse response
    );

    void loginByToken(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    );
}
