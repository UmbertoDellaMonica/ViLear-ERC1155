package it.tcgroup.vilear.registration.Client;

import it.tcgroup.vilear.base.Payload.Request.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "VilearSenderClient",
        url = "${feign.url.sender.host}:${feign.url.sender.port}")
public interface VilearSenderClient {

    @RequestMapping(method = RequestMethod.POST,
            value = "/email/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> sendInviteEmail(
            @RequestBody EmailRequest emailRequest
    );

}
