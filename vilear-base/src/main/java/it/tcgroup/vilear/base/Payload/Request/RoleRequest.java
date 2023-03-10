package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RoleRequest implements Serializable {
    @JsonProperty(value = "role_code")
    @NotNull
    private RoleEnum roleCode;
}
