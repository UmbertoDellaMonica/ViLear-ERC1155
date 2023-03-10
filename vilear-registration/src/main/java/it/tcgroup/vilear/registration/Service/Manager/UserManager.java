package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.registration.Entity.UserEntity;
import it.tcgroup.vilear.registration.Model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserManager {

    /**
     * Salva i dati dell'utente
     * @param userModel contiene i dati dell'utente
     * @return i dati salvati dell'utente
     */
    UserEntity save(UserModel userModel);

    UserEntity get(Integer userId);

    UserEntity getByEmail(String email);

    void delete(Integer userId);

    Page<UserEntity> getAll(PageRequest pageRequest);
}
