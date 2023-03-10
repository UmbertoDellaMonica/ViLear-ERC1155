package it.tcgroup.vilear.registration.Model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserModel {

    private Integer id;

    private String email;

    private String password;

    private Boolean active;

    private Timestamp createdAt;

}
