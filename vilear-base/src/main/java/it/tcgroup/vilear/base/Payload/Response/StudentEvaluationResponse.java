package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class StudentEvaluationResponse implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty( "autonomy") //Valutare da 1 a 10
    private Integer autonomy;
    @JsonProperty( "init_level")
    private Integer initLevel;
    @JsonProperty( "teamwork_level")
    private Integer teamworkLevel;
    @JsonProperty( "end_level")
    private Integer endLevel;
    @JsonProperty( "created_at")
    private Timestamp createdAt;
}
