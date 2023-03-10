package it.tcgroup.vilear.registration.Controller;

import it.tcgroup.vilear.base.Payload.Request.PartnerCandidationRequest;
import it.tcgroup.vilear.base.Payload.Response.PartnerCandidationResponse;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Service.PartnerCandidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/partner")
public class PartnerCandidationController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerCandidationService partnerCandidationService;

    /**
     * Un partner invia la propria candidatura per registrarsi al portale
     * @param partnerCandidationRequest contiene i parametri della PartnerCandidation
     * @return PartnerCandidationResponse
     */
    @PostMapping(path = "/candidate")
    public ResponseEntity<PartnerCandidationResponse> candidate(
            @Valid @RequestBody PartnerCandidationRequest partnerCandidationRequest
    ) {
            PartnerCandidationModel partnerCandidationModel = mapper.map(partnerCandidationRequest, PartnerCandidationModel.class);
            // Candidatura del partner
            partnerCandidationModel = partnerCandidationService.candidate(partnerCandidationModel);
            // Raccolgo i dati ottenuti dalla partneship salvata e anche dall'utente
            PartnerCandidationResponse partnerCandidationResponse = mapper.map(partnerCandidationModel, PartnerCandidationResponse.class);

            return new ResponseEntity<>(partnerCandidationResponse, HttpStatus.OK);
    }



}
