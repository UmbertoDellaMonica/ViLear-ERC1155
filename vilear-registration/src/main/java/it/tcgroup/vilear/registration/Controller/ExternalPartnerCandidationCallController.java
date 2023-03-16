package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Service.PartnerCandidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external/admin")
public class ExternalPartnerCandidationCallController {


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerCandidationService partnerCandidationService;
    /**
     * Chiamata dal Partner-Client per
     * - Modificare l'utente impostando lo stato a True
     * - Modifica lo stato della Partecipazione in APPROVED/REJECTED
     * @param userId id dell'utente
     * @param decision DENY/ACCEPT
     */
    @PutMapping(path = "/candidate/choose")
    public ResponseEntity<PartnerCandidationResponse> choose(
            @RequestParam(name = "user_id") Integer userId,
            @RequestParam(name = "decision") DecisionEnum decision
    ){
        PartnerCandidationModel partnerCandidationModel = partnerCandidationService.choose(userId,decision);
        PartnerCandidationResponse partnerCandidationResponse = mapper.map(partnerCandidationModel,PartnerCandidationResponse.class);

        return new ResponseEntity<>(partnerCandidationResponse, HttpStatus.OK);
    }
}
