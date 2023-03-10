package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Exception.InvitationExpiredExcpetion;
import it.tcgroup.vilear.base.Payload.Exception.UserAlreadyExistsException;
import it.tcgroup.vilear.registration.Model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolePersonService rolePersonService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInvitationService userInvitationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RegistrationModel registration(RegistrationModel registrationModel, UUID token) throws RoleNotFoundException {

        // Verifica che non sia un utente duplicato
        UserModel userModel =  userService.getByEmail(registrationModel.getUser().getEmail());
        if (userModel != null) {
            throw new UserAlreadyExistsException();
        } else {
            userModel = registrationModel.getUser();
        }

        // Cripta la password
        userModel.setPassword(passwordEncoder.encode(registrationModel.getUser().getPassword()));
        // Imposta l'utente come attivo
        userModel.setActive(true);

        // Recupera l'invito
        UserInvitationModel userInvitationModel = userInvitationService.getByToken(token); // Se il token non c'è è null

        // Se è presente un invito, ne verifica la validità
        checkUserInvitation(userInvitationModel);

        // Recupera il ruolo da associare in base all'invito
        RoleModel roleModel = getRoleModelFromInvitation(userInvitationModel);

        // Recupero dei dati anagrafici dell'utente
        PersonModel personModel = registrationModel.getPerson();

        //  Registrazione con Token
        if(userInvitationModel != null) {
            // Associazione dell'utente da creare all'invito recuperato tramite token
            userInvitationModel.setUser(userModel);
            // Salvataggio sul database dell'invito e dell'utente
            userInvitationModel = userInvitationService.add(userInvitationModel);
            userModel = userInvitationModel.getUser();
        } else {
            userModel = userService.add(userModel);
        }
        personModel.setUser(userModel);
        personModel = personService.add(personModel);

        // Aggiunta dell'associazione tra ruolo e persona
        RolePersonModel rolePersonModel = new RolePersonModel(roleModel, personModel);
        rolePersonService.add(rolePersonModel);

        registrationModel.setUser(userModel);
        registrationModel.setPerson(personModel);

        return registrationModel;
    }

    /**
     * Se l'invito è presente
     *      => si devono verificare le seguenti condizioni:
     *          - l'invito non dev'essere scaduto
     *          - l'invito non dev'essere associato a un utenza
     * Altrimenti, non bisogna fare nulla
     * @param userInvitationModel
     */
    private void checkUserInvitation(UserInvitationModel userInvitationModel) {
        if( userInvitationModel != null ) {
            // Verifica che l'invito non sia scaduto
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if( now.after(userInvitationModel.getExpirationDate()) ){
                throw new InvitationExpiredExcpetion();
            }
            // Verifica che la procedura d'invito non sia già stata completata
            if( userInvitationModel.getUser() != null ) {
                throw new UserAlreadyExistsException();
            }
        }
    }

    /**
     * Se l'invito è presente
     *      => si recupera il ruolo relativo all'invito
     * Altrimenti si dà per scontato che la procedura di registrazione sia di uno studente
     */
    private RoleModel getRoleModelFromInvitation(UserInvitationModel userInvitationModel) throws RoleNotFoundException {
        if( userInvitationModel != null ) {
            return userInvitationModel.getRole();
        } else {
            return roleService.get(RoleEnum.STUDENT);
        }
    }

}
