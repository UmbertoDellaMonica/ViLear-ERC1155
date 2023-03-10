package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RolePersonResponse implements Serializable {
    @JsonProperty("role")
    private RoleResponse role;
    @JsonProperty("person")
    private PersonResponse person;
    private Timestamp createdAt;
}
