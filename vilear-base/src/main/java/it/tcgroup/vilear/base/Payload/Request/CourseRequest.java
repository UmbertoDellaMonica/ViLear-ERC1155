package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CourseRequest implements Serializable {
    @JsonProperty("name_course")
    private String name;
    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonProperty("end_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("persons_capacity")
    private Integer capacity;
    @JsonProperty("date_publish")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Timestamp datePublish;

}
