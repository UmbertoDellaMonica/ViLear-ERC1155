package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class UserInvitationRequest implements Serializable {
    @Pattern(
        groups ={PostValidation.class, PatchValidation.class},
        regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$\n"
    )
    @NotNull
    @NotBlank
    private String email;
    private RoleRequest role;
}
