package it.tcgroup.vilear.auth.Redis.Service;

import it.tcgroup.vilear.auth.Model.UserJwtModel;
public interface UserRedisService {

    /**
     * Aggiunge l'utente con il token al Database di Caching
     * @param userRedisModel
     */
    UserJwtModel add(UserJwtModel userRedisModel);

    /**
     * Recupera il singolo Utente tramite
     * @param userId id dello user
     */
    UserJwtModel get(Integer userId);

    /**
     * Recupera il singolo Utente tramite il Token
     * @param token JWT token
     */
    UserJwtModel getByToken(String token);

    /**
     * Recupera l'utente dall'email
     * @param email dell'utente
     */
    UserJwtModel getByEmail(String email);

    /**
     * Verifica che il Token non è scaduto
     *
     * @param userJwtModel contiene i dati del Token
     */
    Boolean isExpiredTokenFromUser(UserJwtModel userJwtModel);

    /**
     * Elimina l'utente :
     * -Logout : 1°Caso
     * -Expired Token  2°Caso
     * @param userRedisModel contiene i dati del Token
     */
    Boolean deleteUser(UserJwtModel userRedisModel);


}
