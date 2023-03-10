package it.tcgroup.vilear.course.Client;

import it.tcgroup.vilear.base.Payload.Request.ToolRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "VilearPartnerClient",
        url = "${feign.url.partner.host}:${feign.url.partner.port}")
public interface VilearPartnerClient {

    /**
     * Call at Vilear-At-Partner
     * @param partnerId id del Partner
     * @param toolId id del tool
     * @param toolRequest contiene i dati del Tool
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/external/partner/tool",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ToolResponse>edit(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "tool_id")Integer toolId,
            @RequestBody ToolRequest toolRequest
    );

    /**
     * Call at Vilear-Partner
     * Recupera se esiste il Teacher
     * @param partnerId id del partner
     * @param teacherId id del Teacher
     */
    @RequestMapping(
                method = RequestMethod.GET,
                value ="/external/partner/teacher",
                produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<TeacherResponse>getTeacher(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "teacher_id")Integer teacherId
    );

    /**
     * Call at Vilear-Partner
     * Recupera se esiste il Tool
     * @param toolId id del tool
     */

    @RequestMapping(
            method = RequestMethod.GET,
            value ="/external/partner/tool",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ToolResponse>getTool(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "tool_id")Integer toolId
    );

    /**
     * Call at Vilear-Partner
     * Recupera se esiste La logistica
     * @param partnerId id del partner
     * @param logisticId id della Logistica
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value ="/external/partner/logistic",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<LogisticResponse>getLogistic(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "logistic_id")Integer logisticId
    );
}
