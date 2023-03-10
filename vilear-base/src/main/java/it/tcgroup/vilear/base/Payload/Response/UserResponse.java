package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("user_passwrd")
    private String password;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
