package it.tcgroup.vilear.partner.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.partner.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Client.VilearRegistrationClient;
import it.tcgroup.vilear.partner.Service.LogisticService;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.partner.Service.TeacherService;
import it.tcgroup.vilear.partner.Service.ToolService;
import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController  {

    private final VilearRegistrationClient vilearRegistrationClient;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;



    /**
     * Accettazione/Rifiuto di una candidatura
     * @param partnerId si riferisce all'id del partner
     * @param decision si riferisce ad ACCEPT/DENY
     * @return PartnerCandidationResponse
     */
    @PutMapping(path = "")
    public ResponseEntity<PartnerCandidationResponse> choose(
        @RequestHeader("partner_id") Integer partnerId,
        @RequestParam("decision") DecisionEnum decision
    ) {
        // Accettazione/Rifiuto della Candidatura
        PartnerModel partnerModel = partnerService.get(partnerId);
        PartnerCandidationResponse partnerCandidationResponse = vilearRegistrationServiceClient.choose(partnerModel,decision);

        return new ResponseEntity<>(partnerCandidationResponse, HttpStatus.OK);
    }

    /**
     * Recupera un singolo Partner
     * @param partnerId id del partner
     */
    @GetMapping(path = "/{partner_id}")
    public ResponseEntity<PartnerResponse> get(
            @PathVariable(name = "partner_id")Integer partnerId,
            HttpServletRequest request

    ){
        PartnerModel partnerModel = partnerService.get(partnerId);
        checkPartner(partnerModel,request);
        PartnerResponse partnerResponse = mapper.map(partnerModel,PartnerResponse.class);
        return new ResponseEntity<>(partnerResponse,HttpStatus.OK);
    }

    private void checkPartner(PartnerModel partnerModel, HttpServletRequest request) {
        String token = jwtService.retrieveToken(request);
        Claims claims = jwtService.getClaimsFromToken(token);
        Integer userId = claims.get("user_id", Integer.class);
        if(!partnerModel.getUserId().equals(userId)){
            throw new Unahautorized();
        }
    }


    /**
     * Recupera la singola Candidazione del Partner
     * @param partnerId id del partner
     * @return
     */
    @GetMapping(path = "/candidate")
    public ResponseEntity<PartnerCandidationResponse>getCandidation(
            @RequestHeader(name = "partner_id")Integer partnerId,
            HttpServletRequest request
    ){
        PartnerModel partnerModel = partnerService.get(partnerId);
        checkPartner(partnerModel,request);
        // Recupero la Partecipazione dall'id dell'utente
        PartnerCandidationResponse partnerCandidationResponse =
                vilearRegistrationServiceClient
                        .getPartnerCandidation(partnerModel.getUserId());

        return new ResponseEntity<>(partnerCandidationResponse,HttpStatus.OK);
    }

}

