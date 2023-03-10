package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PersonRequest implements Serializable {
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("birth_place")
    private String birthPlace;
    @JsonProperty("birthdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
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

}
