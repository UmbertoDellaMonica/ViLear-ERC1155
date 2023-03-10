package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Exception.RolePersonNotFoundException;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Model.PersonModel;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import it.tcgroup.vilear.registration.Service.PartnerCandidationService;
import it.tcgroup.vilear.registration.Service.PersonService;
import it.tcgroup.vilear.registration.Service.RolePersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
public class ExternalRegistrationCallController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerCandidationService partnerCandidationService;

    @Autowired
    private RolePersonService rolePersonService;


    /**
     * Recupera i dati della singola persona
     * @param personId id della persona
     * @return una singola persona
     */
    @GetMapping(path = "/person")
    public ResponseEntity<PersonResponse> get (
            @RequestHeader(name = "person_id")Integer personId
    ){
        // Recupero l'utente
        PersonModel personModel = personService.get(personId);
        PersonResponse personResponse = mapper.map(personModel,PersonResponse.class);

        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    /**
     * Recupera la singola candidazione del Partner
     * @param userId id dell'utente che ha effettuato la candidazione
     */
    @GetMapping(path = "/candidate")
    public ResponseEntity<PartnerCandidationResponse>getPartnerCandidation(
            @RequestHeader(name = "user_id")Integer userId
    ){
        PartnerCandidationModel partnerCandidationModel = partnerCandidationService.getByUser(userId);
        PartnerCandidationResponse partnerCandidationResponse = mapper.map(partnerCandidationModel,PartnerCandidationResponse.class);

        return new ResponseEntity<>(partnerCandidationResponse,HttpStatus.OK);
    }


    /**
     * Recupera il RolePersonResponse con l'id della Persona
     * e il Ruolo di Studente
     * @param role contiene il ruolo
     * @param personId id della Persona
     */
    @GetMapping(path = "/person/role")
    public ResponseEntity<RolePersonResponse> getRolePerson(
            @RequestParam(name = "role_code") RoleEnum role,
            @RequestParam(name = "person_id")Integer personId
    ){
        // Recupera il Ruolo associato alla Persona
        RolePersonModel rolePersonModel = rolePersonService.getRolePerson(role,personId);
        if(rolePersonModel == null){
            throw new RolePersonNotFoundException();
        }
        RolePersonResponse rolePersonResponse = mapper.map(rolePersonModel,RolePersonResponse.class);

        return new ResponseEntity<>(rolePersonResponse,HttpStatus.OK);
    }


}
