package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.registration.Client.ServiceClient.VilearSenderServiceClient;
import it.tcgroup.vilear.registration.Entity.UserInvitationEntity;
import it.tcgroup.vilear.registration.Model.RoleModel;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.Manager.UserInvitationManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserInvitationServiceImpl implements UserInvitationService{

    @Autowired
    private VilearSenderServiceClient vilearSenderServiceClient;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Logger logger;

    @Autowired
    private RoleService roleService;

     @Autowired
     private UserInvitationManager userInvitationManager;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserInvitationModel invite(UserInvitationModel userInvitationModel) throws RoleNotFoundException {
        // Recupero del ruolo
        RoleModel roleModel = roleService.get(userInvitationModel.getRole().getRoleCode());
        // Impostazione dei dati dell'invito
        userInvitationModel.setUser(null);
        userInvitationModel.setRole(roleModel);
        userInvitationModel.setActive(true);
        userInvitationModel.createDateTimeExpired();
        if(vilearSenderServiceClient.sendInviteEmail(userInvitationModel)){
            logger.info("Email was Sent...");
            return  add(userInvitationModel);
        }
        return null;
    }

    @Override
    @Transactional
    public UserInvitationModel add(UserInvitationModel userInvitationModel) {
        UserInvitationEntity userInvitationEntity = userInvitationManager.save(userInvitationModel);

        return mapper.map(userInvitationEntity,UserInvitationModel.class);
    }

    @Override
    @Transactional
    public UserInvitationModel get(Integer userInvitationId) {
        UserInvitationEntity userInvitationEntity = userInvitationManager.get(userInvitationId);

        return mapper.map(userInvitationEntity,UserInvitationModel.class);
    }

    @Override
    @Transactional
    public UserInvitationModel getByToken(UUID token) {
        UserInvitationEntity userInvitationEntity = userInvitationManager.getByToken(token);
        if (userInvitationEntity == null) {
            return null;
        } else {
            return mapper.map(userInvitationEntity, UserInvitationModel.class);
        }
    }

    @Override
    @Transactional
    public PaginatedResponse<UserInvitationModel> filter(Integer page, Integer pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        Page<UserInvitationEntity>userInvitationEntityPage = userInvitationManager.getAll(pageRequest);

        List<UserInvitationModel>userInvitationModelList = mapper.map(userInvitationEntityPage.getContent(),new TypeToken<List<UserModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(userInvitationEntityPage.getTotalPages());
        pageData.setTotalElements(userInvitationEntityPage.getTotalElements());

        PaginatedResponse<UserInvitationModel>paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setPageData(pageData);
        paginatedResponse.setData(userInvitationModelList);

        return paginatedResponse;
    }

}
