package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.*;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Model.RolePersonModel;
import it.tcgroup.vilear.registration.Model.UserInvitationModel;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.PartnerCandidationService;
import it.tcgroup.vilear.registration.Service.RolePersonService;
import it.tcgroup.vilear.registration.Service.UserInvitationService;
import it.tcgroup.vilear.registration.Service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminstratorController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerCandidationService partnerCandidationService;

    @Autowired
    private UserInvitationService userInvitationService;

    @Autowired
    private RolePersonService rolePersonService;

    @Autowired
    private UserService userService;

    /**
     * Recupera Tutte le candidazioni del Partner
     * @param page numero della Pagina
     * @param pageSize dimensione della Pagina
     * @return
     */
    @GetMapping(path = "/partner/candidate")
    public ResponseEntity<PaginatedResponse<PartnerCandidationResponse>> filterPartnerCandidations(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize
    ) {
        // Recupero di tutte le candidature
        PaginatedResponse<PartnerCandidationModel> paginatedModel = partnerCandidationService.getPartnerCandidations(page,pageSize);
        List<PartnerCandidationResponse> partnerCandidationResponseList = mapper.map(
                paginatedModel.getData(),
                new TypeToken<List<PartnerCandidationResponse>>() {}.getType()
        );
        PaginatedResponse<PartnerCandidationResponse> partnerCandidationResponse = new PaginatedResponse<>(
                partnerCandidationResponseList,
                paginatedModel.getPageData()
        );

        return new ResponseEntity<>(partnerCandidationResponse, HttpStatus.OK);
    }

    /**
     * Elimina il singolo utente
     * @param userId si riferisce all'id dell'utente
     */
    @DeleteMapping(path = "user/{user_id}")
    public ResponseEntity<Boolean> delete (
            @PathVariable(name = "user_id")Integer userId
    ) {
        // Elimino l'utente
        userService.delete(userId);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    /**
     * Recupera tutti gli utenti
     * @param page numero pagine
     * @param pageSize dimensione della pagina
     */
    @GetMapping(path = "/user")
    public ResponseEntity<PaginatedResponse<UserResponse>> filter(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize
    ) {
        // Recupero tutti gli users
        PaginatedResponse<UserModel> userModelList = userService.getUsers(page,pageSize);
        PaginatedResponse<UserResponse>userResponsePaginatedResponse = mapper.map(userModelList,new TypeToken<PaginatedResponse<UserResponse>>(){}.getType());

        return new ResponseEntity<>(userResponsePaginatedResponse, HttpStatus.OK);
    }


    /**
     * Recupera tutti gli inviti
     * @param page si riferisce al numero della pagina da visualizzare
     * @param pageSize si riferisce a quanti elementi ci sono nella pagina
     * @return List: UserInvitationResponse
     */
    @GetMapping(path = "/user/invitation")
    public ResponseEntity<PaginatedResponse<UserInvitationResponse>>filterInvitation(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize
    ) {
        PaginatedResponse<UserInvitationModel> userInvitationModelPage = userInvitationService.filter(page,pageSize);
        PaginatedResponse<UserInvitationResponse> paginatedResponse = mapper.map(userInvitationModelPage, new TypeToken<PaginatedResponse<UserInvitationResponse>>(){}.getType());

        return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
    }

    /**
     * Estrazione di tutte le Persone con quel Ruolo
     * @param role Ruolo
     * @param page pagina da visualizzare
     * @param pageSize numero degli elementi da contenere in pagina
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<RolePersonResponse>>filterRolePersonByRole(
            @RequestParam(name = "role_label")String role,
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize) throws RoleNotFoundException {
        // Recupero le Persone con quel Ruolo
        PaginatedResponse<RolePersonModel>rolePersonModelList = rolePersonService.getPersonsByRole(role,page,pageSize);
        PaginatedResponse<RolePersonResponse>rolePersonResponseList=mapper.map(rolePersonModelList,new TypeToken<PaginatedResponse<RolePersonResponse>>(){}.getType());

        return new ResponseEntity<>(rolePersonResponseList,HttpStatus.OK);
    }

}
