package it.tcgroup.vilear.registration.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Setter
public class UserInvitationModel {

    private Integer id;

    private UserModel user;

    private RoleModel role;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp landingAt;

    private Timestamp expirationDate;

    private String email;

    private UUID invitationToken = UUID.randomUUID();

    private Boolean active;

    public void createDateTimeExpired(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 1);
        this.expirationDate = new Timestamp( now.getTime().getTime() );
    }

}
