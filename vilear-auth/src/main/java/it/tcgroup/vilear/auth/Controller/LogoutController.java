package it.tcgroup.vilear.auth.Controller;

import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private JwtServiceAuth jwtService;

    @DeleteMapping(path = "")
    public ResponseEntity<Boolean>logout(
            HttpServletRequest request
    ){
        String token = jwtService.retrieveToken(request);
        UserJwtModel userJwtModel = userRedisService.getByToken(token);
        if(userJwtModel!=null){
            userRedisService.deleteUser(userJwtModel);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
