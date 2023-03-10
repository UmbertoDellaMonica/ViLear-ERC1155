package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PartnerCandidationResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user")
    private UserResponse user;

    @JsonProperty("partner")
    private PartnerResponse partner;

    @JsonProperty("note")
    private String note;

    @JsonProperty("request_date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp requestDate;

    @JsonProperty("decision_date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp decisionDate;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @JsonProperty("created_at")
    private Timestamp createdAt;
}
