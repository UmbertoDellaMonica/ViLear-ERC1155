package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import it.tcgroup.vilear.base.Payload.Validation.PatchValidation;
import it.tcgroup.vilear.base.Payload.Validation.PostValidation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserRequest implements Serializable {
    @JsonProperty("email")
    @Pattern(
            groups ={PostValidation.class, PatchValidation.class},
            regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])"
    )
    @NotNull @NotBlank
    private String email;
    @Pattern(
            groups = {PostValidation.class,PatchValidation.class},
            regexp = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$$"
    )
    @JsonProperty("user_passwrd")
    private String password;

}
