package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Entity.RoleEntity;
import it.tcgroup.vilear.registration.Model.RoleModel;
import it.tcgroup.vilear.registration.Service.Manager.RoleManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleManager roleManager;

    @Override
    @Transactional
    public RoleModel getRoleByLabel(String roleLabel) throws RoleNotFoundException {
        RoleEntity roleEntity = roleManager.getRoleByLabel(roleLabel);

        return mapper.map(roleEntity, RoleModel.class);
    }

    @Override
    @Transactional
    public RoleModel get(RoleEnum roleEnum) throws RoleNotFoundException {

        RoleEntity roleEntity = roleManager.getRoleByRoleCode(roleEnum);

        return mapper.map(roleEntity, RoleModel.class);
    }

}
