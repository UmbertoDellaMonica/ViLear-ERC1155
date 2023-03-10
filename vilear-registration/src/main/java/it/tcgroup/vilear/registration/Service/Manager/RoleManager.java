package it.tcgroup.vilear.registration.Service.Manager;


import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Entity.RoleEntity;

import javax.management.relation.RoleNotFoundException;

public interface RoleManager {
    RoleEntity getRoleByLabel(String roleLabel) throws RoleNotFoundException;

    RoleEntity getRoleByRoleCode(RoleEnum roleEnum) throws RoleNotFoundException;
}
