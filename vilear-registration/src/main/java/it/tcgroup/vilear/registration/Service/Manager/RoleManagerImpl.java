package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Entity.RoleEntity;
import it.tcgroup.vilear.registration.Repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleManagerImpl implements RoleManager{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleByLabel(String roleLabel) throws RoleNotFoundException {
        return roleRepository
                .findByRoleLabel(roleLabel)
                .orElseThrow(()->new RoleNotFoundException());

    }

    @Override
    public RoleEntity getRoleByRoleCode(RoleEnum roleEnum) throws RoleNotFoundException {
        return roleRepository
                .findById(roleEnum)
                .orElseThrow(()->new RoleNotFoundException());

    }
}
