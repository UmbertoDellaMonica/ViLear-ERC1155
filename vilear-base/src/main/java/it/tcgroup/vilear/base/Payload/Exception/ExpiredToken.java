package it.tcgroup.vilear.base.Payload.Exception;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
public class ExpiredToken  extends RuntimeException{
    private String token;

    private String message;

    private FilterChain filterChain;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ExpiredToken(
            String token,
            String message,
            FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        this.token = token;
        this.message = message;
        this.filterChain = filterChain;
        this.request = request;
        this.response = response;
    }
}
