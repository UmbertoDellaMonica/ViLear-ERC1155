package it.tcgroup.vilear.registration.Model;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class RolePersonModel {

    @Enumerated(EnumType.STRING)
    private RoleModel role;

    private PersonModel person;


    private Timestamp createdAt;

    public RolePersonModel(RoleModel role, PersonModel person) {
        this.role = role;
        this.person = person;
    }

    public void setRoleCode(RoleEnum roleCode) {
        if( role == null ) {
            role = new RoleModel();
        }
        role.setRoleCode(roleCode);
    }

    public void setPersonId(Integer personId) {
        if( person == null ) {
            person = new PersonModel();
        }
        person.setId(personId);
    }

}
