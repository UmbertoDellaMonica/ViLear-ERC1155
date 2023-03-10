package it.tcgroup.vilear.auth.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtServiceAuth jwtService;

    @Autowired
    private UserRedisService userRedisService;

    @GetMapping(path = "/verify")
    public ResponseEntity<UserAuthenticationResponse> verifyToken(
            @RequestHeader(name = "token")String token
    ) {
        try {
            UserAuthenticationResponse userAuthenticationResponse = buildAuth(token);
            return new ResponseEntity<>(userAuthenticationResponse, HttpStatus.OK);
        }catch(ExpiredJwtException e){
            // recupero il token
            UserJwtModel userJwtModel = userRedisService.getByToken(token);
            if(userJwtModel!=null){
                userRedisService.deleteUser(userJwtModel);
            }
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/verify/manager")
    public ResponseEntity<UserAuthenticationResponse> getAuthByUsername(
            @RequestHeader(name = "username")String username
    ){

        UserAuthenticationResponse userAuthenticationResponse = buildAuthByUsername(username);
        return new ResponseEntity<>(userAuthenticationResponse, HttpStatus.OK);
    }

    private UserAuthenticationResponse buildAuthByUsername(String username) {
        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        UserJwtModel userJwtModel = userRedisService.getByEmail(username);
        if(userJwtModel!=null){
            Claims claims = jwtService.getClaimsFromToken(userJwtModel.getToken());
            List<SimpleGrantedAuthority> grantedAuthorityList = jwtService.getAuthoritiesFromToken(userJwtModel.getToken(),claims);
            List<String>roles = new ArrayList<>();
            userAuthenticationResponse.setPassword(userJwtModel.getPassword());
            userAuthenticationResponse.setEmail(userJwtModel.getEmail());
            for(SimpleGrantedAuthority s: grantedAuthorityList){
                roles.add(s.getAuthority());
            }
            userAuthenticationResponse.setRoles(roles);
            return userAuthenticationResponse;
        }
        throw new Unahautorized();
    }


    private UserAuthenticationResponse buildAuth(String token) {
        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        UserJwtModel userJwtModel = userRedisService.getByToken(token);
        if(userJwtModel!=null){
            Claims claims = jwtService.getClaimsFromToken(token);
            List<SimpleGrantedAuthority> grantedAuthorityList = jwtService.getAuthoritiesFromToken(token,claims);
            List<String>roles = new ArrayList<>();
            userAuthenticationResponse.setPassword(userJwtModel.getPassword());
            userAuthenticationResponse.setEmail(userJwtModel.getEmail());
            for(SimpleGrantedAuthority s: grantedAuthorityList){
                roles.add(s.getAuthority());
            }
            userAuthenticationResponse.setRoles(roles);
            return userAuthenticationResponse;
        }
        throw new Unahautorized();
    }

}
