package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserInvitationResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("landing_at")
    private Timestamp landingAt;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private RoleResponse role;
    @JsonProperty("user")
    private UserResponse user;
    @JsonProperty("expiration_date")
    private Timestamp expirationDate;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("invitation_token")
    private UUID invitationToken;
}
