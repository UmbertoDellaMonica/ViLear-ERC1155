package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherCourseRequest implements Serializable {
    @JsonProperty("partner_id")
    private Integer partnerId;
    @JsonProperty("person_id")
    private Integer teacherId;

}
