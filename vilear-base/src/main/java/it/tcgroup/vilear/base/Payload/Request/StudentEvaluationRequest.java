package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class StudentEvaluationRequest implements Serializable {
    @JsonProperty("autonomy")
    private Integer autonomy;
    @JsonProperty("init_level")
    private Integer initLevel;
    @JsonProperty("teamwork_level")
    private Integer teamworkLevel;
    @JsonProperty("end_level")
    private Integer endLevel;

}
