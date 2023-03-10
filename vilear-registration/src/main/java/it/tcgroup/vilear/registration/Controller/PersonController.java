package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Request.PersonRequest;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.registration.Model.PersonModel;
import it.tcgroup.vilear.registration.Service.PersonService;
import it.tcgroup.vilear.registration.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;


    /**
     * Recupera i dati della singola persona
     * @param personId id della persona
     * @return una singola persona
     */
    @GetMapping(path = "/person/{person_id}")
    public ResponseEntity<PersonResponse> get (
            @PathVariable(name = "person_id")Integer personId
    ){
        // Recupero l'utente
        checkUser(personId);
        PersonModel personModel = personService.get(personId);
        PersonResponse personResponse = mapper.map(personModel,PersonResponse.class);

        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }


    /**
     * Aggiorna i dati della Persona
     * @param personId id della Persona
     * @param personRequest contiene i dati da modificare
     * @return i dati aggiornati della Persona
     */
    @PutMapping(path = "/{person_id}")
    public ResponseEntity<PersonResponse> edit(
        @PathVariable(name = "person_id")Integer personId,
        @RequestBody PersonRequest personRequest
    ) {
        checkUser(personId);
        PersonModel personModel = mapper.map(personRequest, PersonModel.class);
        // Aggiorna i dati della Persona
        personModel = personService.update(personModel,personId);
        PersonResponse personResponse = mapper.map(personModel,PersonResponse.class);

        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    private void checkUser(Integer personId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();// Si riferisce all'username
        PersonModel personModel = personService.get(personId);
        if(personModel!=null) {
            if (!personModel.getUser().getEmail().equals(email)) {
                throw new Unahautorized();
            }
        }else {
            throw new Unahautorized();
        }
    }

}
