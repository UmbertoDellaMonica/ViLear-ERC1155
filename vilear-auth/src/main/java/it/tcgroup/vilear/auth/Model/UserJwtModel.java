package it.tcgroup.vilear.auth.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserJwtModel {
    private Integer id;
    private String token;
    private String password;
    private String email;
    private Date releaseDate;
    private Date expiredDate;
}
