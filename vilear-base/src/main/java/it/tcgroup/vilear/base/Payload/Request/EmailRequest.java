package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailRequest implements Serializable {

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("toPerson")
    private String to;

    @JsonProperty("role")
    private String role;

}
