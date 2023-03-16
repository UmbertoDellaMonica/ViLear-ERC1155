package it.tcgroup.vilear.partner.Controller;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.*;
import it.tcgroup.vilear.partner.Client.ServiceClient.VilearCourseServiceClient;
import it.tcgroup.vilear.partner.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.partner.Client.VilearCourseClient;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Service.LogisticService;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.partner.Service.ToolService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/partner")
public class AdministratorController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ToolService toolService;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;
    @Autowired
    private VilearCourseServiceClient vilearCourseServiceClient;

    /**
     * Aggiorna un partner ad Amministratore
     * @param partnerId id del partner
     * @param administrator si riferisce allo stato TRUE/FALSE per l'amministrativo
     * @return PartnerResponse
     */
    @PutMapping(path = "/{partner_id}")
    public ResponseEntity<PartnerResponse>setAdministrator(
            @PathVariable(name = "partner_id")Integer partnerId,
            @RequestParam(name = "administrator")Boolean administrator
    ){
        // Aggiorna il partner ad Amministratore
        PartnerModel partnerModel = partnerService.setAdministrator(partnerId, administrator);
        PartnerResponse partnerResponse = mapper.map(partnerModel,PartnerResponse.class);

        return new ResponseEntity<>(partnerResponse, HttpStatus.OK);
    }


    /**
     * Eliminazione di una Logistica
     * @param partnerId id del partner
     * @param logisticId id della logistica
     */
    @DeleteMapping(path = "/{logistic_id}")
    public ResponseEntity<Boolean>deleteLogistic(
            @RequestHeader(name = "partner_id") Integer partnerId,
            @PathVariable(name = "logistic_id") Integer logisticId
    ){
        // Elimina la logistica associata a quel partner
        partnerService.deleteLogistic(logisticId, partnerId);
        if(vilearCourseServiceClient.deleteLogistics(logisticId)) {

            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    /**
     * Eliminazione di un Docente
     * @param partnerId id del partner
     * @param teacherId id Docente
     */
    @DeleteMapping(path = "/{teacher_id}")
    public ResponseEntity<Boolean>deleteTeacher(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @PathVariable(name = "teacher_id")Integer teacherId
    ){
        // Elimina un Teacher associato al partner
        partnerService.deleteTeacher(teacherId, partnerId);
        if(vilearCourseServiceClient.deleteTeachers(teacherId)) {

            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        throw new RuntimeException("EXCEPTION");
    }

    /**
     * Eliminazione del Tool
     * @param partnerId id del partner
     * @param toolId id del tool
     */
    @DeleteMapping(path = "/{tool_id}")
    public ResponseEntity<Boolean>deleteTool(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @PathVariable(name = "tool_id")Integer toolId
    ){
        // Elimina un Tool associato al partner
        if(vilearCourseServiceClient.deleteTools(toolId)) {
            partnerService.deleteTool(toolId, partnerId);
        }
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

    /**
     * Recupera tutte le Candidature dei partner
     * @param page
     * @param pageSize
     * @return una Lista di candidature del Partner
     */
    @GetMapping(path = "/candidate")
    public ResponseEntity<PaginatedResponse<PartnerCandidationResponse>>filterCandidation(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize
    ) {

        // Recupero di tutte le candidature
        // Devo fare una chiamata
        PaginatedResponse<PartnerCandidationResponse>paginationCandidation =
                vilearRegistrationServiceClient.getPartnerCandidations(page,pageSize);
        return new ResponseEntity<>(paginationCandidation,HttpStatus.OK);
    }

    /**
     * Recupera tutti i Partner
     * @param page si riferisce al numero delle Pagine
     * @param pageSize si riferisce alla dimensione delle pagine
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<PartnerResponse>>filterPartner(
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PaginatedResponse<PartnerModel>partnerModelPaginatedResponse = partnerService.getPartners(page,pageSize);
        PaginatedResponse<PartnerResponse>partnerResponsePaginatedResponse = mapper.map(partnerModelPaginatedResponse,new TypeToken<PaginatedResponse<PartnerResponse>>(){}.getType());

        return new ResponseEntity<>(partnerResponsePaginatedResponse,HttpStatus.OK);
    }

}
