package it.tcgroup.vilear.auth.Client;

import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "VilearPartnerClient",
        url = "${feign.url.partner.host}:${feign.url.partner.port}"
)
public interface VilearPartnerClient {
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
