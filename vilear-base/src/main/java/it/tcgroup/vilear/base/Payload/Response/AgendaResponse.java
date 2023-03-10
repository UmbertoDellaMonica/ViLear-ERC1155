package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AgendaResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("course_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date courseDate;
    @JsonProperty("time_begin")
    private Time timeBegin;
    @JsonProperty("time_end")
    private Time timeEnd;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
