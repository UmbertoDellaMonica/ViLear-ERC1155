package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Exception.RolePersonNotFoundException;
import it.tcgroup.vilear.registration.Entity.RolePersonEntity;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import it.tcgroup.vilear.registration.Repository.RolePersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RolePersonManagerImpl implements RolePersonManager {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RolePersonRepository rolePersonRepository;

    @Override
    public Boolean exists(RoleEnum roleCode, Integer personId) {
        RolePersonEntity.PrimaryKey primaryKey = new RolePersonEntity.PrimaryKey(roleCode, personId);

        return rolePersonRepository.existsById(primaryKey);
    }

    @Override
    public RolePersonEntity save(RolePersonModel rolePersonModel){

        RolePersonEntity.PrimaryKey primaryKey = new RolePersonEntity.PrimaryKey(rolePersonModel.getRole().getRoleCode(), rolePersonModel.getPerson().getId());
        RolePersonEntity rolePersonEntity = mapper.map(rolePersonModel, RolePersonEntity.class);
        rolePersonEntity.setPrimaryKey(primaryKey);

        return rolePersonRepository.save(rolePersonEntity);
    }

    @Override
    public void deleteRolePerson(RoleEnum roleCode, Integer personId){
        RolePersonEntity.PrimaryKey primaryKey=new RolePersonEntity.PrimaryKey(roleCode,personId);
        if(rolePersonRepository.existsById(primaryKey)) {
            rolePersonRepository.deleteById(primaryKey);
        }else{
            throw new RolePersonNotFoundException();
        }
    }

    @Override
    public RolePersonEntity get(RoleEnum role,Integer personId){
        return rolePersonRepository.findById(new RolePersonEntity.PrimaryKey(role,personId))
                .orElse(null);
    }

    @Override
    public Page<RolePersonEntity> findByRole(String role, PageRequest pageRequest) {
        return rolePersonRepository.findByRole(role,pageRequest);
    }

}
