package it.tcgroup.vilear.registration.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PersonModel {

    private Integer id;

    private UserModel user;

    private List<RolePersonModel> rolePersonList;

    private String firstname;

    private String birthPlace;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthDate;

    private String lastname;

    private String phoneNumber;

    private String zipCode;

    private String city;

    private String addressResidence;

    private String educationalQualification;

    private UUID cv=UUID.randomUUID();

    @CreationTimestamp
    private Timestamp createdAt;

}
