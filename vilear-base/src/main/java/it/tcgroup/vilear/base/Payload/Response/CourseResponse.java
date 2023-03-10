package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CourseResponse implements Serializable {
    @JsonProperty( "id")
    private Integer id;
    @JsonProperty("name_course")
    private String name;
    @JsonProperty("date_publish")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datePublish;
    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonProperty("expiration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("persons_capacity")
    private Integer capacity;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("status")
    private StatusEnum status;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @JsonProperty("course_id")
    private AgendaResponse agenda;
    public void createDateTimeExpired(){
        Date date=getEndDate();
        date.setMonth(date.getMonth()-1);
        this.setEndDate(date);
    }
}
