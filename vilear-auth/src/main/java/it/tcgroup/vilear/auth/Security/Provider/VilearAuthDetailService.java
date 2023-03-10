package it.tcgroup.vilear.auth.Security.Provider;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.auth.Client.ServiceClient.VilearPartnerServiceClient;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import org.springframework.beans.factory.annotation.Value;
import it.tcgroup.vilear.auth.Entity.PartnerCandidationEntity;
import it.tcgroup.vilear.auth.Entity.PersonEntity;
import it.tcgroup.vilear.auth.Entity.RolePersonEntity;
import it.tcgroup.vilear.auth.Entity.UserEntity;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Repository.PartnerCandidationRepository;
import it.tcgroup.vilear.auth.Repository.PersonRepository;
import it.tcgroup.vilear.auth.Repository.RolePersonRepository;
import it.tcgroup.vilear.auth.Repository.UserRepository;
import it.tcgroup.vilear.auth.Security.Service.JwtServiceAuth;
import it.tcgroup.vilear.base.Payload.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VilearAuthDetailService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private VilearPartnerServiceClient vilearPartnerServiceClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PartnerCandidationRepository partnerCandidationRepository;

    @Autowired
    private RolePersonRepository rolePersonRepository;

    @Autowired
    private JwtServiceAuth jwtService;

    @Value("${user.super_admin.email}")
    private String userSuperAdminEmail;

    @Value("${user.super_admin.password}")
    private String userSuperAdminPassword;

    /**
     * Recupera e verifica l'utente come Admin avendo
     * Le credenziali giuste
     * @param email contiene l'email dell'admin
     */
    private User checkUserAsAdmin(String email) {
        User user = null;
        // Verifico che l'email non sia uguale a quella del Super Admin
        if(email.equalsIgnoreCase(userSuperAdminEmail)){
            // Setto il Ruolo
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(RoleEnum.SUPER_ADMIN.getRole());
            // Imposto i dati dell'utente
            user = new User(email,passwordEncoder.encode(userSuperAdminPassword), List.of(grantedAuthority));
            return user;
        }
        return user;
    }

    /**
     * Recupera dalla cache un'utente con quell'email
     * E con anche i suoi ruoli
     * @param email dell'utente
     */
    private User checkUserFromCache(String email) {
        // Recupero l'utente con il token JWT se esiste
        User user = null;
        UserJwtModel userJwtModel = userRedisService.getByEmail(email);
        if(userJwtModel!=null) {
            if (jwtService.isExpiredToken(userJwtModel.getToken())) {
                userRedisService.deleteUser(userJwtModel);
                return user;
            } else {
                Claims claims = jwtService.getClaimsFromToken(userJwtModel.getToken());

                List<SimpleGrantedAuthority> grantedAuthorityList = jwtService.getAuthoritiesFromToken(userJwtModel.getToken(), claims);
                user = new User(email, claims.get("password").toString(), grantedAuthorityList);
            }
        }
        return user;
    }

    @Transactional
    private User buildUserPerson(PersonEntity personEntity) {
        User user = null;
        // Verifico che i ruoli dell'utente non siano vuoti
        if(personEntity.getRolePersonList().isEmpty()){
            throw new RuntimeException("PERSON NOT HAVE a ROLE");
        }
        // Recupera la lista delle Autorities che servono per la Persona
        List<SimpleGrantedAuthority>simpleGrantedAuthorityList = buildAuthorityList(personEntity.getRolePersonList());
        // Instanzia un nuovo utente
        user = new User(personEntity.getUser().getEmail(), personEntity.getUser().getPassword(), simpleGrantedAuthorityList);
        return user;
    }


    private List<SimpleGrantedAuthority> buildAuthorityList(List<RolePersonEntity> rolePersonList) {
        List<String>roleOfPerson = rolePersonList.stream().map(
                rolePersonEntity -> rolePersonEntity.getRole().getRoleLabel()
        ).collect(Collectors.toList());
        List<SimpleGrantedAuthority>simpleGrantedAuthorityList = new ArrayList<>();
        for(String role: roleOfPerson){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            simpleGrantedAuthorityList.add(simpleGrantedAuthority);
        }
        return simpleGrantedAuthorityList;
    }

    @Transactional
    private User buildUserPartner(PartnerCandidationEntity partnerCandidationEntity) {
        User user = null;
        PartnerResponse partnerResponse = vilearPartnerServiceClient.getPartnerByUser(partnerCandidationEntity.getUser().getId());
        if (partnerResponse != null) {
            List<SimpleGrantedAuthority>simpleGrantedAuthorityList = new ArrayList<>();
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(RoleEnum.PARTNER.getRole()));
            if (partnerResponse.getAdmin()) {
                simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(RoleEnum.SUPER_PARTNER.getRole()));
            }
            user = new User(
                    partnerCandidationEntity.getUser().getEmail(),
                    partnerCandidationEntity.getUser().getPassword(),
                    simpleGrantedAuthorityList
            );
        }
        return user;
    }


    /**
     * Recupera l'utente dal Db
     * @param email contiene l'email dell'utente
     */
    private User checkUserFromDb(String email) {
        User user = null;
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        //L'utente che si sta loggando ci deve essere, altrimenti lanciamo un'eccezione
        if(userEntity.isEmpty()){
            throw new UserNotFoundException();
        }

        Integer userId = userEntity.get().getId();
        // Altrimenti devo verificare se questa è una Persona o un Partner
        /*
            Se questa è una Persona, questa avrà il ruolo di :
            -STUDENTE
            -PARTNER
         */
        Optional<PersonEntity>personEntity = personRepository.findPersonByIdUser(userId);

        Optional<PartnerCandidationEntity>partnerCandidationEntity = partnerCandidationRepository.findByUser(userId);

        // Verifico che questi non coesistano
        if(personEntity.isPresent() && partnerCandidationEntity.isPresent()){
            throw new RuntimeException("PARTNER e PERSON non possono coesistere");
        }
        // Verifico che esista l'utente come Persona e prendo i suoi ruoli
        if(personEntity.isPresent()){
            user = buildUserPerson(personEntity.get());
        }

        // Verifico che esista l'utente come Partner
        if(partnerCandidationEntity.isPresent()){
            user = buildUserPartner(partnerCandidationEntity.get());
        }

        return user;
    }




    /**
     * Recupero le informazioni dell'utente
     * Vi sono due casi :
     * - UTENTE ADMIN
     * - UTENTE NORMALE :
     * 1) Utente non ha acceduto neanche una volta e quindi al momento della Login
     *    I suoi dati non verranno visualizzati e quindi lo user sarà a Null
     * 2) Utente ha acceduto e quindi i suoi dati in quel momento sono accessibili all'interno di Redis
     *    Si possono visualizzare :
     * - Email
     * - Password
     * - Id
     * - Create Date
     * - Expiration Date
     * - Token
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = null;
        // Verifico che non sia il SuperAdmin

        user = checkUserAsAdmin(email);// Questo non si potrà mai trovare in cache

        if(user!=null){
            return user;
        }


        // Se lo trova qui dentro è già autenticato
        user = checkUserFromCache(email);


        /*
            Se non è il SUPER_ADMIN e non è un'utente Loggato
            Devo recuperarlo dal Database di Vilear-Registration
         */
        if(user == null) {
            // Se sono nulli quelli precedenti
            // Cerco l'utente all'interno del Database
            user = checkUserFromDb(email);
            // L'utente potrà essere una persona oppure un Partner a seconda dell'email che inserisce

        }

        return user;
    }
}
