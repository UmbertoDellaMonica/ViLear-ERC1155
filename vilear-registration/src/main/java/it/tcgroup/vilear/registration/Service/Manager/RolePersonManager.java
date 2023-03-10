package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Entity.RolePersonEntity;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RolePersonManager {

    Boolean exists(RoleEnum roleCode, Integer personId);

    RolePersonEntity save(RolePersonModel rolePersonModel);

    void deleteRolePerson(RoleEnum roleCode, Integer personId);

    RolePersonEntity get(RoleEnum role, Integer personId);

    Page<RolePersonEntity> findByRole(String role, PageRequest pageRequest);
}
