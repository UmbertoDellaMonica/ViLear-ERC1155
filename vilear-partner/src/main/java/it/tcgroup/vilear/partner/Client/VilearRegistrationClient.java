package it.tcgroup.vilear.partner.Client;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "vilearRegistrationClient",
        url = "${feign.url.registration.host}:${feign.url.registration.port}")
public interface VilearRegistrationClient {

    /**
     * Call at Vilear-Registration
     * Accetta o Rifiuta lo stato della Candidatura
     * @param userId id dello user in Vilear-Registration
     * @param decisionEnum ACCEPT/DENY
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "/external/admin/candidate/choose",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PartnerCandidationResponse> choose(
            @RequestParam(name = "user_id") Integer userId,
            @RequestParam(name = "decision") DecisionEnum decisionEnum
    );

    /**
     * Call at Vilear-Registration
     * Recupera tutte le Candidazioni dei Partner
     * @param page numero pagina
     * @param pageSize dimensione della Pagina
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/admin/partner/candidate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaginatedResponse<PartnerCandidationResponse>> getPartnerCandidations(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize
    );

    /**
     * Call at Vilear-Registration
     * Recupera i dati della Persona
     * @param personId id della Persona
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/external/person",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PersonResponse> getPerson(
            @RequestHeader(name = "person_id")Integer personId
    );

    /**
     * Call at Vilear-Registration
     * Recupera la candidazione del Partner
     * @param userId id dello user che ha effettuato la candidazione
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/external/partner/candidate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PartnerCandidationResponse> getPartnerCandidation(
            @RequestParam(name = "user_id") Integer userId
    );


}
