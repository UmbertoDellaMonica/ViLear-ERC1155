package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Request.RegistrationRequest;
import it.tcgroup.vilear.base.Payload.Response.RegistrationResponse;
import it.tcgroup.vilear.registration.Model.RegistrationModel;
import it.tcgroup.vilear.registration.Service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("user/registration")
public class RegistrationController {

    @Autowired
    private ModelMapper mapper;

   @Autowired
   private RegistrationService registrationService;

    /**
     * Registrazione di un'utente tramite una registration Request
     * Permette di registrare sia utenti non invitati e sia utenti invitati
     * @param registrationRequest Contiene i dati del partner
     * @param token univoco per poter accedere alla registrazione
     * @return RegistrationResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<RegistrationResponse> register(
        @Valid @RequestBody RegistrationRequest registrationRequest,
        @RequestParam(name = "token", required = false) UUID token
    ) throws RoleNotFoundException {
        RegistrationModel registrationModel = mapper.map(registrationRequest, RegistrationModel.class);
        // Registrazione dell'utente
        registrationModel = registrationService.registration(registrationModel, token);
        RegistrationResponse registrationResponse = mapper.map(registrationModel, RegistrationResponse.class);

        return new ResponseEntity<>(registrationResponse, HttpStatus.OK);
    }

}
