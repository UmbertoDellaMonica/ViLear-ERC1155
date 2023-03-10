package it.tcgroup.vilear.registration.Entity;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {
    @Id
    @Column(name = "role_code")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleCode;

    @Column(name = "role_label")
    private String roleLabel;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
