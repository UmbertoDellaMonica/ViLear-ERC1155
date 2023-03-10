package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationResponse implements Serializable {
    @JsonProperty("person")
    private PersonResponse person;
}
