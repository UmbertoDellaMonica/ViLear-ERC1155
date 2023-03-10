package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PersonResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("user")
    private UserResponse user;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("birth_place")
    private String birthPlace;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("zip_code")
    private String zipCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("address_residence")
    private String addressResidence;
    @JsonProperty("educational_qualification")
    private String educationalQualification;
    @JsonProperty("cv")
    private UUID cv;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @JsonProperty("birthdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
}
