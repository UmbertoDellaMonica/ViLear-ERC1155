package it.tcgroup.vilear.auth.Redis.Manager;


import it.tcgroup.vilear.auth.Model.UserJwt;
import it.tcgroup.vilear.auth.Model.UserJwtModel;

public interface UserRedisManager {

    /**
     * Salva un'utente in cache
     * @param userRedisModel contiene i dati dello UserLoggato
     */
    UserJwt save(UserJwtModel userRedisModel);

    /**
     * Recupera l'utente che ha già generato il Token
     * @param userId id dell'utente
     */
    UserJwt get(Integer userId);

    /**
     * Recupera l'utente attraverso il token che viene passato tramite Request
     * @param token token JWT
     */
    UserJwt getByToken(String token);

    /**
     * Recupera lo user dall'email
     * @param email dell'utente
     */
    UserJwt getByEmail(String email);

    /**
     * Elimina un utente tramite
     * @param userId l'id dello user
     */
    void delete(Integer userId);


}
