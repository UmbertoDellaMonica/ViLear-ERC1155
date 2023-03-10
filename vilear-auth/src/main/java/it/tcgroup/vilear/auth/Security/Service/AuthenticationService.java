package it.tcgroup.vilear.auth.Security.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    /**
     * Esegue l'estrazione dei dati dal token
     * Setta le impostazioni di Autenticazione
     *
     * @param token
     */
    void setAuthentication(String token);

    /**
     * Esegue una Nuova Autenticazione dell'utente nell'esecuzione di una request
     * @param request
     * @param response
     */
    void newAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    );
    /**
     * Verifica che il Token non sia null
     * @param request
     */
    Boolean checkToken(HttpServletRequest request);

    /**
     * Verifico Username e Password affinchè nessuna delle due non deve essere null
     * @param request
     */
    void checkUsernameAndPassword(HttpServletRequest request);
}
