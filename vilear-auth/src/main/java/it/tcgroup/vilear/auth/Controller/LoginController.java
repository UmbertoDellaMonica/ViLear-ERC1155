package it.tcgroup.vilear.auth.Controller;

import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import it.tcgroup.vilear.base.Payload.Response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JwtServiceAuth jwtService;

    @Autowired
    private UserRedisService userRedisService;

    /**
     * Effettuo la login dell'utente
     * L'utente deve passare i parametri di Username e Password
     * Questi sono stati già validati
     */
    @GetMapping(path = "")
    public ResponseEntity<JwtResponse>login(
           @NotNull @RequestParam(name = "username") String username,
           @NotNull @RequestParam(name = "password") String password,
           HttpServletRequest request
    ) {
        UserJwtModel userJwtModel = userRedisService.getByEmail(username);
        if(userJwtModel!=null){
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(userJwtModel.getToken());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        JwtResponse jwtResponse = buildJwt(username);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


    /**
     * Costruisce un Jwt totalmente nuovo
     * @param username email dell'utente
     */
    private JwtResponse buildJwt( String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtService.buildToken(authentication, username));
        return jwtResponse;
    }

}
