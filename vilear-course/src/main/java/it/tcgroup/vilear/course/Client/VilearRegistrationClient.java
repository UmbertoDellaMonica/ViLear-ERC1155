package it.tcgroup.vilear.course.Client;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "VilearRegistrationClient",
        url = "${feign.url.registration.host}:${feign.url.registration.port}")
public interface VilearRegistrationClient {

    /**
     * Call at Vilear-Registration
     * Recupera il Ruolo della Persona
     * @param role ruolo della Persona
     * @param personId id della Persona
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/external/person/role",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<RolePersonResponse> getRolePerson(
            @RequestParam(name = "role_code") RoleEnum role,
            @RequestParam(name = "person_id")Integer personId
    );

    /**
     * Call at Vilear-Registration
     * Recupera i dati della Persona
     * @param personId id della Persona
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/external/person",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PersonResponse> getPerson(
            @RequestHeader(name = "person_id")Integer personId
    );
}
