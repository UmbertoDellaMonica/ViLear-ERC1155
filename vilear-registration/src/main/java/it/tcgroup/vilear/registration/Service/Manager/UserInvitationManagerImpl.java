package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.UserInvitationNotFoundException;
import it.tcgroup.vilear.registration.Entity.UserInvitationEntity;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import it.tcgroup.vilear.registration.Repository.UserInvitationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserInvitationManagerImpl implements UserInvitationManager {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserInvitationRepository userInvitationRepository;

    @Override
    public UserInvitationEntity save(UserInvitationModel userInvitationModel) {
        UserInvitationEntity userInvitationEntity=mapper.map(userInvitationModel,UserInvitationEntity.class);

        return userInvitationRepository.save(userInvitationEntity);
    }

    @Override
    public UserInvitationEntity get(Integer userInvitationId){
        return userInvitationRepository
                .findById(userInvitationId)
                .orElseThrow(()->new UserInvitationNotFoundException());
    }

    @Override
    public UserInvitationEntity getByToken(UUID token) {
        return userInvitationRepository
                .findByToken(token)
                .orElse(null);
    }

    @Override
    public Page<UserInvitationEntity> getAll(PageRequest pageRequest) {
        return userInvitationRepository.findAll(pageRequest);
    }

}
