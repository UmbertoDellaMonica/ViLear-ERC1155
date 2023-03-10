package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolCourseResponse implements Serializable {

    @JsonProperty("tool_id")
    private Integer toolId;
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
