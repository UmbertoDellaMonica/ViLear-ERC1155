package it.tcgroup.vilear.auth.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email",nullable = false)
    @NotBlank
    private String email;


    @Column(name = "user_passwrd",unique = true, nullable = false)
    private String password;

    @Column(name = "active",nullable = false)
    private Boolean active;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;
}
