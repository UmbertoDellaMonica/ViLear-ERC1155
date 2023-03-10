package it.tcgroup.vilear.registration.Client;

import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "VilearAuthClient",
        url = "${feign.url.auth.host}:${feign.url.auth.port}"
)
public interface VilearAuthClient {

    /**
     * Call-at-Vilear-Auth
     * Questa chiamata deve :
     * Verificare che il token che sto passando sia :
     * - Valido (Estrazione e verifica dell'utente)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/auth/verify",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UserAuthenticationResponse>verifyToken(
            @RequestHeader(name = "token")String token
    );

    /**
     * Call-at-Vilear-Auth
     * Questa chiamata deve :
     * Verificare che il token che sto passando sia :
     * - Valido (Estrazione e verifica dell'utente)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/auth/verify/manager",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UserAuthenticationResponse>verifyTokenByUser(
            @RequestHeader(name = "username")String username
    );


}
