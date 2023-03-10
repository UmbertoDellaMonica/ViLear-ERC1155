package it.tcgroup.vilear.registration.Client;

import it.tcgroup.vilear.base.Payload.Request.PartnerRequest;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "VilearPartnerClient",
        url = "${feign.url.partner.host}:${feign.url.partner.port}")
public interface VilearPartnerClient {

    /**
     * Call at Vilear-Partner
     * Aggiunge un Partner
     * @param partnerRequest contiene i dati del Partner
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "external/partner/save",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PartnerResponse> addPartner(
            @RequestBody PartnerRequest partnerRequest
    );

    /**
     * Call at Vilear-Partner
     * Recupera il Singolo Partner
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "external/partner/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PartnerResponse> getPartnerByUser(
            @RequestHeader(name = "user_id") Integer userId
    );
}
