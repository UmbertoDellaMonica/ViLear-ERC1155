package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TeacherCourseResponse implements Serializable {
    @JsonProperty("Id")
    private Integer teacherId;
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
