package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AgendaRequest implements Serializable {
    @JsonProperty(value = "course_date",required = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date courseDate;
    @JsonProperty("time_begin")
    @JsonFormat(pattern="HH:mm:ss")
    private Time timeBegin;
    @JsonProperty("time_end")
    @JsonFormat(pattern="HH:mm:ss")
    private Time timeEnd;

}
