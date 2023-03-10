package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RoleResponse implements Serializable {
    @JsonProperty("role_code")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleCode;
    @JsonProperty("label_role")
    private String label;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
