package it.tcgroup.vilear.registration.Model;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
@Getter
@Setter
public class RoleModel {

    @Enumerated(EnumType.STRING)
    private RoleEnum roleCode;

    private String roleLabel;

    private Timestamp createdAt;

}
