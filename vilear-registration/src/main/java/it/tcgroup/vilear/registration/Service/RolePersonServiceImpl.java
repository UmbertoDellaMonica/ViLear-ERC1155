package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.registration.Entity.RolePersonEntity;
import it.tcgroup.vilear.registration.Model.RoleModel;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import it.tcgroup.vilear.registration.Service.Manager.RolePersonManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class RolePersonServiceImpl implements RolePersonService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RolePersonManager rolePersonManager;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public RolePersonModel add(RolePersonModel rolePersonModel) {
        // Imposta l'associazione tra ruolo e persona
        RolePersonEntity rolePersonEntity = rolePersonManager.save(rolePersonModel);

        return mapper.map(rolePersonEntity, RolePersonModel.class);
    }

    @Override
    @Transactional
    public PaginatedResponse<RolePersonModel> getPersonsByRole(String role, Integer page, Integer pageSize) throws RoleNotFoundException {
        //Ruolo esiste -> altrimenti eccezione
        RoleModel roleModel = roleService.getRoleByLabel(role);

        PageRequest pageRequest= PaginationUtils.buildPageRequest(page,pageSize);
        Page<RolePersonEntity> rolePersonEntityPage = rolePersonManager.findByRole(roleModel.getRoleCode().getRole(),pageRequest);

        List<RolePersonModel>rolePersonModelList = mapper.map(rolePersonEntityPage.getContent(),new TypeToken<List<RolePersonModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData(rolePersonEntityPage.getTotalPages(),rolePersonEntityPage.getTotalElements());

        PaginatedResponse<RolePersonModel> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setData(rolePersonModelList);
        paginatedResponse.setPageData(pageData);

        return paginatedResponse;
    }

    @Override
    @Transactional
    public RolePersonModel getRolePerson(RoleEnum role, Integer personId) {
        RolePersonEntity rolePersonEntity = rolePersonManager.get(role,personId);
        if(rolePersonEntity==null){
            return null;
        }else{
            return mapper.map(rolePersonEntity, RolePersonModel.class);
        }
    }

    @Override
    @Transactional
    public void deleteRolePerson(RoleEnum roleCode, Integer personId) {
        rolePersonManager.deleteRolePerson(roleCode,personId);
    }

}
