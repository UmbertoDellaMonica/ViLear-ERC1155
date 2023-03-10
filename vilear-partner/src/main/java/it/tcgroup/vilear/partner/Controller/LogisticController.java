package it.tcgroup.vilear.partner.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Service.LogisticService;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.base.Payload.Request.LogisticRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
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
@RequestMapping("/logistic")
public class LogisticController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private JwtService jwtService;
    /**
     * Inserimento di una Logistica
     * @param partnerId id del partner
     * @param logisticRequest contiene le informazione della logistica
     * @return LogisticResponse
     */
    @PostMapping(path = "/add")
    public ResponseEntity<LogisticResponse> add(
        @RequestHeader(name = "partner_id") Integer partnerId,
        @RequestBody LogisticRequest logisticRequest
    ){
        LogisticModel logisticModel = mapper.map(logisticRequest, LogisticModel.class);
        //Inserimento dei dati della  Logistica
        logisticModel= partnerService.addLogistic(partnerId,logisticModel);
        LogisticResponse logisticResponse = mapper.map(logisticModel,LogisticResponse.class);

        return new ResponseEntity<>(logisticResponse, HttpStatus.OK);
    }



    /**
     * Recupero della singola logistica
     * @param partnerId id Partner
     * @param logisticId id Tool
     * @return una singola Tool
     */
    @GetMapping(path = "/{logistic_id}")
    public ResponseEntity<LogisticResponse> get (
        @RequestHeader(name = "partner_id") Integer partnerId,
        @PathVariable(name = "logistic_id") Integer logisticId,
        HttpServletRequest request
    ) {
        // Recupero e Verifica di quel Tool
        LogisticModel logisticModel = logisticService.get(logisticId, partnerId);
        checkLogistic(logisticModel,request);
        LogisticResponse logisticResponse = mapper.map(logisticModel, LogisticResponse.class);

        return new ResponseEntity<>(logisticResponse, HttpStatus.OK);
    }


    /**
     * Aggiornamento di una Logistica
     * @param logisticId id della Logistica
     * @param logisticRequest contiene i dati della Logistica
     * @return una Logistica aggiornata
     */
    @PutMapping(path = "/{logistic_id}")
    public ResponseEntity<LogisticResponse>editLogistic(
            @PathVariable(name = "logistic_id") Integer logisticId,
            @RequestHeader(name = "partner_id") Integer partnerId,
            @RequestBody LogisticRequest logisticRequest,
            HttpServletRequest request
    ){
        // Recupero e mappatura della Logistica
        LogisticModel logisticModel = mapper.map(logisticRequest, LogisticModel.class);
        checkLogistic(logisticService.get(logisticId,partnerId),request);
        // Aggiornamento della Logistica
        logisticModel = logisticService.update(logisticModel, logisticId, partnerId);
        LogisticResponse logisticResponse = mapper.map(logisticModel,LogisticResponse.class);

        return new ResponseEntity<>(logisticResponse,HttpStatus.OK);
    }


    private void checkLogistic(LogisticModel logisticModel, HttpServletRequest request) {
        String token = jwtService.retrieveToken(request);
        Claims claims = jwtService.getClaimsFromToken(token);
        Integer userId = claims.get("user_id", Integer.class);
        if(!logisticModel.getPartner().getUserId().equals(userId)){
            throw new Unahautorized();
        }
    }

    /**
     * Recupero della singola Logistica
     * @param partnerId id Partner
     * @return una singola Logistica
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<LogisticResponse>> filter (
        @RequestHeader(name = "partner_id") Integer partnerId,
        @RequestParam(name = "page") int page,
        @RequestParam(name = "page_size") int pageSize,
        HttpServletRequest request
    ) {
        // Recupero e la lista delle logistiche a partire da un partner
        PaginatedResponse<LogisticModel> logisticModelPage = logisticService.getByPartnerId(partnerId, page, pageSize);
        if(logisticModelPage.getData().size()>0){
            checkLogistic(logisticModelPage.getData().get(0),request);
        }
        List<LogisticResponse> logisticResponseList = mapper.map(logisticModelPage.getData(), new TypeToken<List<LogisticModel>>(){}.getType());;

        PaginatedResponse<LogisticResponse> logisticResponsePaginated = new PaginatedResponse<>(
                logisticResponseList,
                logisticModelPage.getPageData()
        );

        return new ResponseEntity<>(logisticResponsePaginated, HttpStatus.OK);
    }



}
