package it.tcgroup.vilear.partner.Controller;

import it.tcgroup.vilear.base.Payload.Exception.TeacherNotFoundException;
import it.tcgroup.vilear.base.Payload.Request.LogisticRequest;
import it.tcgroup.vilear.base.Payload.Request.PartnerRequest;
import it.tcgroup.vilear.base.Payload.Request.ToolRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.partner.Service.LogisticService;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.partner.Service.TeacherService;
import it.tcgroup.vilear.partner.Service.ToolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/external/partner")
public class ExternalPartnerCallController {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private TeacherService teacherService;


    @Autowired
    private ModelMapper mapper;

    /**
     * Effettua l'aggiunta di un Partner
     * @param partnerRequest contiene i dati del Partner
     */
    @PostMapping(path = "/save")
    public ResponseEntity<PartnerResponse> add(
            @Valid @RequestBody PartnerRequest partnerRequest
    ){
        // Recupero l'utente e la sua partnership e salvo i dati nel Partner
        PartnerModel partnerModel = mapper.map(partnerRequest,PartnerModel.class);
        partnerModel = partnerService.add(partnerModel);
        PartnerResponse newPartnerResponse = mapper.map(partnerModel,PartnerResponse.class);

        return new ResponseEntity<>(newPartnerResponse, HttpStatus.OK);
    }

    /**
     * Recupera un singolo Partner
     * @param userId id dello user appartenente al Partner
     */
    @GetMapping(path = "/user")
    public ResponseEntity<PartnerResponse> getPartnerByUser(
            @RequestHeader(name = "user_id")Integer userId
    ){
        PartnerModel partnerModel = partnerService.getByUser(userId);
        if(partnerModel == null){
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        PartnerResponse partnerResponse = mapper.map(partnerModel,PartnerResponse.class);
        return new ResponseEntity<>(partnerResponse,HttpStatus.OK);
    }


    /**
     * Recupero del singolo Tool
     * @param toolId id Tool
     * @param partnerId id Partner
     * @return una singola Tool
     */
    @GetMapping(path = "/tool")
    public ResponseEntity<ToolResponse> getTool(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "tool_id")Integer toolId,
            HttpServletRequest request
    ){
        // Recupero e Verifica di quel Tool
        ToolModel toolModel = toolService.get(toolId,partnerId);
        ToolResponse toolResponse = mapper.map(toolModel,ToolResponse.class);

        return new ResponseEntity<>(toolResponse,HttpStatus.OK);
    }

    /**
     * Aggiornamento del Tool
     * @param partnerId id del Partner
     * @param toolId id del Tool
     * @param toolRequest contiene le informazioni del Tool
     * @return un Tool Aggiornato
     */
    @PutMapping(path = "/tool")
    public ResponseEntity<ToolResponse>editTool(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestHeader(name = "tool_id")Integer toolId,
            HttpServletRequest request,
            @RequestBody ToolRequest toolRequest
    ){
        //Recupero e Mappatura di un Tool
        ToolModel toolModel= mapper.map(toolRequest,ToolModel.class);
        // Aggiornamento del Tool
        toolModel = toolService.update(toolModel,toolId,partnerId);
        ToolResponse toolResponse = mapper.map(toolModel,ToolResponse.class);

        return new ResponseEntity<>(toolResponse,HttpStatus.OK);
    }


    /**
     * Recupero della singola logistica
     * @param partnerId id Partner
     * @param logisticId id Tool
     * @return una singola Tool
     */
    @GetMapping(path = "/logistic")
    public ResponseEntity<LogisticResponse> getLogistic (
            @RequestHeader(name = "partner_id") Integer partnerId,
            @RequestHeader(name = "logistic_id") Integer logisticId,
            HttpServletRequest request
    ) {
        // Recupero e Verifica di quel Tool
        LogisticModel logisticModel = logisticService.get(logisticId, partnerId);
        LogisticResponse logisticResponse = mapper.map(logisticModel, LogisticResponse.class);

        return new ResponseEntity<>(logisticResponse, HttpStatus.OK);
    }


    /**
     * Aggiornamento di una Logistica
     * @param logisticId id della Logistica
     * @param logisticRequest contiene i dati della Logistica
     * @return una Logistica aggiornata
     */
    @PutMapping(path = "/logistic")
    public ResponseEntity<LogisticResponse>editLogistic(
            @RequestHeader(name = "logistic_id") Integer logisticId,
            @RequestHeader(name = "partner_id") Integer partnerId,
            @RequestBody LogisticRequest logisticRequest,
            HttpServletRequest request
    ){
        // Recupero e mappatura della Logistica
        LogisticModel logisticModel = mapper.map(logisticRequest, LogisticModel.class);
        // Aggiornamento della Logistica
        logisticModel = logisticService.update(logisticModel, logisticId, partnerId);
        LogisticResponse logisticResponse = mapper.map(logisticModel,LogisticResponse.class);

        return new ResponseEntity<>(logisticResponse,HttpStatus.OK);
    }


    /**
     * Recupera un singolo Docente
     * @param teacherId id della Persona
     * @param partnerId id del Partner
     * @return un singolo Docente
     */
    @GetMapping(path = "/teacher")
    public ResponseEntity<TeacherResponse>get(
            @RequestHeader(name = "teacher_id") Integer teacherId,
            @RequestHeader(name = "partner_id") Integer partnerId,
            HttpServletRequest request
    ) {
        // Recupero e Verifica di un Docente
        TeacherModel teacherModel = teacherService.get(teacherId, partnerId);
        if (teacherModel == null) {
            throw new TeacherNotFoundException();
        } else {

            TeacherResponse teacherResponse = mapper.map(teacherModel, TeacherResponse.class);

            return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
        }
    }

}
