package it.tcgroup.vilear.auth.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "persons")
@Getter
@Setter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;


    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
    private List<RolePersonEntity> rolePersonList;

    @Column(name = "firstname",nullable = false)
    private String firstname;

    @Column(name = "birth_place",nullable = false)
    private String birthPlace;

    @Column(name = "birthdate",nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthDate;

    @Column(name = "lastname",nullable = false)
    private String lastname;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "zip_code",nullable = false)
    private String zipCode;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "address_residence",nullable = false)
    private String addressResidence;

    @Column(name = "educational_qualification")
    private String educationalQualification;

    @Column(name = "cv")
    private UUID cv=UUID.randomUUID();

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
