package it.tcgroup.vilear.partner.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.partner.Service.ToolService;
import it.tcgroup.vilear.base.Payload.Request.ToolRequest;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private JwtService jwtService;


    /**
     * Inserimento di uno Strumento
     * @param partnerId id del partner
     * @param toolRequest contiene le informazione del tool
     * @return ToolResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<ToolResponse> add(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestBody ToolRequest toolRequest
    ){
        ToolModel toolModel = mapper.map(toolRequest, ToolModel.class);
        // Inserimento dei dati di un Tool
        toolModel = partnerService.addTool(partnerId,toolModel);
        ToolResponse toolResponse = mapper.map(toolModel, ToolResponse.class);

        return new ResponseEntity<>(toolResponse, HttpStatus.OK);
    }

    /**
     * Recupero del singolo Tool
     * @param toolId id Tool
     * @param partnerId id Partner
     * @return una singola Tool
     */
    @GetMapping(path = "/{tool_id}")
    public ResponseEntity<ToolResponse> get(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @PathVariable(name = "tool_id")Integer toolId,
            HttpServletRequest request
    ){
        // Recupero e Verifica di quel Tool
        ToolModel toolModel = toolService.get(toolId,partnerId);
        checkTool(toolModel,request);
        ToolResponse toolResponse = mapper.map(toolModel,ToolResponse.class);

        return new ResponseEntity<>(toolResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutti i Tool associati a quel Partner
     * @param partnerId id del Partner
     * @return una Lista di Tool
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<ToolResponse>> filterTools(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize,
            HttpServletRequest request
    ) {
        // Recupera tutti i Tool associati a quel Partner
        PaginatedResponse<ToolModel>toolResponsePaginatedResponse= toolService.getByPartner(partnerId,page,pageSize);
        if(toolResponsePaginatedResponse.getData().size()>0){
            checkTool(toolResponsePaginatedResponse.getData().get(0),request);
        }
        List<ToolResponse>toolResponseList=mapper.map(toolResponsePaginatedResponse.getData(), new TypeToken<List<ToolResponse>>(){}.getType());
        PaginatedResponse<ToolResponse> toolResponsePage = new PaginatedResponse<>(
                toolResponseList,
                toolResponsePaginatedResponse.getPageData()
        );

        return new ResponseEntity<>(toolResponsePage,HttpStatus.OK);
    }

    private void checkTool(ToolModel toolModel, HttpServletRequest request) {
            String token = jwtService.retrieveToken(request);
            Claims claims = jwtService.getClaimsFromToken(token);
            Integer userId = claims.get("user_id", Integer.class);
            if(!toolModel.getPartner().getUserId().equals(userId)){
                throw new Unahautorized();
            }
    }

    /**
     * Aggiornamento del Tool
     * @param partnerId id del Partner
     * @param toolId id del Tool
     * @param toolRequest contiene le informazioni del Tool
     * @return un Tool Aggiornato
     */
    @PutMapping(path = "/{tool_id}")
    public ResponseEntity<ToolResponse>editTool(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @PathVariable(name = "tool_id")Integer toolId,
            HttpServletRequest request,
            @RequestBody ToolRequest toolRequest
    ){
        //Recupero e Mappatura di un Tool
        ToolModel toolModel= mapper.map(toolRequest,ToolModel.class);
        checkTool(toolService.get(toolId,partnerId),request);
        // Aggiornamento del Tool
        toolModel = toolService.update(toolModel,toolId,partnerId);
        ToolResponse toolResponse = mapper.map(toolModel,ToolResponse.class);

        return new ResponseEntity<>(toolResponse,HttpStatus.OK);
    }



}
