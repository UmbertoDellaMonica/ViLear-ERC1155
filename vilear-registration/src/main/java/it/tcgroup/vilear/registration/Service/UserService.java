package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.registration.Model.UserModel;
import org.springframework.data.domain.Page;

public interface UserService {

    UserModel add(UserModel userModel);

    /**
     * Recupero un singolo Utente
     * @param userId id dell'utente
     * @return un singolo Utente
     */
    UserModel get(Integer userId);

    /**
     * Recupero di un singolo Utente tramite la sua Mail :
     * @param email dell'utente
     * @return un singolo Utente
     */
    UserModel getByEmail(String email);

    /**
     * Recupera tutti gli utenti
     * @param page pagina
     * @param pageSize numero di elementi della pagina
     * @return una Lista di Utenti
     */
    PaginatedResponse<UserModel> getUsers(int page, int pageSize);

    /**
     * Aggiorna un'utente
     * @param userModifyModel contiene i dati dell'utente modificato
     * @param userId id dell'utente
     * @return un singolo Utente modificato
     */
    UserModel update(UserModel userModifyModel, Integer userId);

    /**
     * Elimina un'Utente tramite il suo Id
     * @param userId id dell'utente
     */
    void delete(Integer userId);

}
