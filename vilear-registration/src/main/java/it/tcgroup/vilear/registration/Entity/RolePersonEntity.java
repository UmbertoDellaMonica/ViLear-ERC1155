package it.tcgroup.vilear.registration.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;

@Entity
@Table(name = "roles_persons")
@Getter
@Setter
public class RolePersonEntity {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PrimaryKey implements Serializable{

        @Column(name = "role_code")
        @Enumerated(EnumType.STRING)
        private RoleEnum roleCode;

        @Column(name = "person_id")
        private Integer personId;

        public PrimaryKey(RoleEnum roleCode, Integer personId) {
            this.roleCode = roleCode;
            this.personId = personId;
        }

    }

    @EmbeddedId
    private PrimaryKey primaryKey;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleCode")
    @JoinColumn(name = "role_code")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    public void setPerson(PersonEntity person) {
        this.person = person;
        if( primaryKey == null ) {
            primaryKey = new PrimaryKey();
        }
        primaryKey.setPersonId(person.getId());
    }

    public void setRole(RoleEntity role) {
        this.role = role;
        if( primaryKey == null ) {
            primaryKey = new PrimaryKey();
        }
        primaryKey.setRoleCode(role.getRoleCode());
    }

}
