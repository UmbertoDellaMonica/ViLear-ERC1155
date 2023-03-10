package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.registration.Entity.UserInvitationEntity;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface UserInvitationManager {
    /**
     * Salva i dati dell'invito
     * @param userInvitationModel contiene i dati dell'invito
     * @return un invito
     */
    UserInvitationEntity save(UserInvitationModel userInvitationModel);

    UserInvitationEntity get(Integer userInvitationId);

    UserInvitationEntity getByToken(UUID token);

    Page<UserInvitationEntity> getAll(PageRequest pageRequest);
}
