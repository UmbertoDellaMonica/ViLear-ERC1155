package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Request.UserInvitationRequest;
import it.tcgroup.vilear.base.Payload.Response.UserInvitationResponse;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.UserInvitationService;
import it.tcgroup.vilear.registration.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "user/invitation")
public class UserInvitationController {

    @Autowired
    private UserInvitationService userInvitationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;


    /**
     * Invio di un invito di registrazione
     * @param userInvitationRequest contiene le informazioni che servono per quella determinata persona
     * @return UserInvitationResponse
     * Authorize: SuperAdmin
     */
    @PostMapping(path = "")
    public ResponseEntity<UserInvitationResponse> invite(
        @Valid @RequestBody UserInvitationRequest userInvitationRequest
    ) throws RoleNotFoundException {
        UserInvitationModel userInvitationModel = mapper.map(userInvitationRequest, UserInvitationModel.class);
        // Inoltro dell'invito per l'utente
        userInvitationModel = userInvitationService.invite(userInvitationModel);
        UserInvitationResponse userInvitationResponse = mapper.map(userInvitationModel, UserInvitationResponse.class);

        return new ResponseEntity<>(userInvitationResponse, HttpStatus.OK);
    }

    private void checkUser(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();// Si riferisce all'username
        UserModel userModel = userService.get(userId);
        if(userModel!=null) {
            if (!userModel.getId().equals(userId)) {
                throw new Unahautorized();
            }
        }else {
            throw new Unahautorized();
        }
    }

    /**
     * Estrazione dei dati di un singolo utente
     * @param userInvitationId si riferisce all'id dell'invito
     * @return UserInvitationResponse
     * Authorize: STUDENT,TEACHER
     */
    @GetMapping(path = "/{user_invitation_id}")
    public ResponseEntity<UserInvitationResponse> getUserInvitation(
        @PathVariable(name = "user_invitation_id") Integer userInvitationId,
        HttpServletRequest request
    ) {

        UserInvitationModel userInvitationModel = userInvitationService.get(userInvitationId);
        // Verifica se Tale elemento appartiene all'utente autenticato
        checkUser(userInvitationModel.getUser().getId());
        UserInvitationResponse userInvitationResponse = mapper.map(userInvitationModel,UserInvitationResponse.class);
        return new ResponseEntity<>(userInvitationResponse, HttpStatus.OK);
    }
}
