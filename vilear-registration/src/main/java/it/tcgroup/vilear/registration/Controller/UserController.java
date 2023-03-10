package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Request.UserRequest;
import it.tcgroup.vilear.base.Payload.Response.UserResponse;
import it.tcgroup.vilear.registration.Client.ServiceClient.VilearAuthServiceClient;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private VilearAuthServiceClient vilearAuthServiceClient;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    /**
     * Recupera il singolo utente
     * @param userId id dell'utente
     * @return  un singolo utente
     * Authorities: Student,Teacher,Partner,SuperPartner
     */
    @GetMapping(path = "/{user_id}")
    public ResponseEntity<UserResponse> get(
        @PathVariable(name = "user_id") Integer userId,
        HttpServletRequest request
    ) {
        // Recupero il singolo User
        // Verifica se l'id dello User è lo stesso
            checkUser(userId);
            UserModel userModel = userService.get(userId);

            UserResponse userResponse = mapper.map(userModel, UserResponse.class);

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    private void checkUser(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();// Si riferisce all'username
        UserModel userModel = userService.getByEmail(email);
        if(userModel!=null) {
            if (!userModel.getId().equals(userId)) {
                throw new Unahautorized();
            }
        }else {
            throw new Unahautorized();
        }
    }


    /**
     * Aggiorna i dati dell'email e password dell'utente
     * @param userId id dell'utente
     * @param userRequest contiene i dati dell'utente modificato
     * @return un Utente modificato
     * Authorizies: Student,Teacher,Partner,SuperPartner
     */
    @PutMapping(path = "/{user_id}")
    public ResponseEntity<UserResponse> edit(
            @PathVariable(name = "user_id")Integer userId,
            @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ){
                checkUser(userId);
                UserModel userModel = mapper.map(userRequest, UserModel.class);

                // Aggiorna i dati dell'utente : password e email
                userModel = userService.update(userModel, userId);
                UserResponse userResponse = mapper.map(userModel, UserResponse.class);

                return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
