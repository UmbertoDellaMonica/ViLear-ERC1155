package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LogisticCourseResponse implements Serializable {

    @JsonProperty("logistics_id")
    private Integer logisticId;
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
