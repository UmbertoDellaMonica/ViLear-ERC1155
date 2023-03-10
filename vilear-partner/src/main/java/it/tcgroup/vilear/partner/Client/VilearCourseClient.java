package it.tcgroup.vilear.partner.Client;

import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "VilearCourseClient",
        url = "${feign.url.course.host}:${feign.url.course.port}"
)
public interface VilearCourseClient {

    /**
     * Call-at-Vilear-Course
     * Modifico lo status del Tool
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/external/tool",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Boolean> deleteTools(
            @RequestHeader(name = "tool_id")Integer toolId
    );

    /**
     * Call-at-Vilear-Course
     * Modifico lo status del Tool
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/external/teacher",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Boolean> deleteTeachers(
            @RequestHeader(name = "teacher_id")Integer toolId
    );

    /**
     * Call-at-Vilear-Course
     * Modifico lo status del Tool
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/external/logistic",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Boolean> deleteLogistics(
            @RequestHeader(name = "logistic_id")Integer toolId
    );
}
