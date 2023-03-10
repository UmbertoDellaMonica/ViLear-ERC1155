package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Exception.RolePersonAlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Request.RolePersonRequest;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import it.tcgroup.vilear.registration.Service.PersonService;
import it.tcgroup.vilear.registration.Service.RolePersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user/role")
public class UserRoleController {

    @Autowired
    private RolePersonService rolePersonService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PersonService personService;


    /**
     * Aggiunge un Ruolo di una Persona
     * @param rolePersonRequest contiene il ruolo di una Persona
     * @return un ruolo di una persona
     * Authorize: Student,Teacher
     */
    @PostMapping(path = "")
    public ResponseEntity<RolePersonResponse> addRole(
        @RequestBody RolePersonRequest rolePersonRequest,
        HttpServletRequest request
    ){
        // Aggiungo il ruolo per quella Persona
        // Verifica che l'utente che sta inserendo i dati sia lo stesso della Login
         RolePersonModel rolePersonModel = rolePersonService.getRolePerson(rolePersonRequest.getRoleCode(), rolePersonRequest.getPersonId());
        if(rolePersonModel!=null){
            throw new RolePersonAlreadyExistsException();
        }else{
            rolePersonModel = mapper.map(rolePersonRequest, RolePersonModel.class);
            rolePersonModel = rolePersonService.add(rolePersonModel);
        }
        RolePersonResponse rolePersonResponse = mapper.map(rolePersonModel,RolePersonResponse.class);

        return new ResponseEntity<>(rolePersonResponse, HttpStatus.OK);
    }



    /**
     * Elimina un Ruolo associato ad una Persona
     * @param personId id della Persona
     * @param roleCode Ruolo
     */
    @DeleteMapping(path = "/{person_id}")
    public ResponseEntity<Boolean>delete(
        @PathVariable(name = "person_id") Integer personId,
        @RequestParam(name = "role_code") RoleEnum roleCode,
        HttpServletRequest request
    ){
        // Elimina il Ruolo associato a quella Persona
        rolePersonService.deleteRolePerson(roleCode, personId);

        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

}
