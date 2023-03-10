package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RegistrationRequest implements Serializable {
    @JsonProperty("user")
    @NotNull
    private UserRequest user;
    @JsonProperty("person")
    @NotNull
    private PersonRequest person;

}
