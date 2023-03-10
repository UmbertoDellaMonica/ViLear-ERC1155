package it.tcgroup.vilear.partner.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Service.PartnerService;
import it.tcgroup.vilear.partner.Service.TeacherService;
import it.tcgroup.vilear.base.Payload.Exception.TeacherNotFoundException;
import it.tcgroup.vilear.base.Payload.Request.TeacherRequest;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private JwtService jwtService;

    /**
     * Inserimento di un Docente
     * @param partnerId id del Partner
     * @param teacherRequest contiene le informazioni del docente
     * @return TeacherResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<TeacherResponse> add(
            @RequestHeader(name = "partner_id")Integer partnerId,
            @RequestBody TeacherRequest teacherRequest
    ){

        // Inserimento dei dati del Docente
        TeacherModel teacherModel = partnerService.addTeacher(teacherRequest.getTeacherId(),partnerId);
        TeacherResponse teacherResponse = mapper.map(teacherModel,TeacherResponse.class);

        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    /**
     * Recupera un singolo Docente
     * @param teacherId id della Persona
     * @param partnerId id del Partner
     * @return un singolo Docente
     */
    @GetMapping(path = "/{teacher_id}")
    public ResponseEntity<TeacherResponse>get(
            @PathVariable(name = "teacher_id") Integer teacherId,
            @RequestHeader(name = "partner_id") Integer partnerId,
            HttpServletRequest request
    ) {
        // Recupero e Verifica di un Docente
        TeacherModel teacherModel = teacherService.get(teacherId, partnerId);
        if (teacherModel == null) {
            throw new TeacherNotFoundException();
        } else {
            checkTeacher(teacherModel,request);

            TeacherResponse teacherResponse = mapper.map(teacherModel, TeacherResponse.class);

            return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
        }
    }

    /**
     * Recupero di tutti i docenti associati a quel Partner
     * @param partnerId id del partner
     * @return una lista di tutti i docenti
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<TeacherResponse>> filterTeachers(
            @RequestHeader(name = "partner_id") Integer partnerId,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "page_size") Integer pageSize,
            HttpServletRequest request

    ){
        // Recupera tutti i docenti associati a quel partnerId
        PaginatedResponse<TeacherModel> paginatedModel = teacherService.getByPartner(partnerId,page,pageSize);
        if(paginatedModel.getData().size()>0){
            checkTeacher(paginatedModel.getData().get(0),request);
        }
        List<TeacherResponse> teacherResponseList= mapper.map(paginatedModel.getData(), new TypeToken<List<TeacherResponse>>(){}.getType());
        PaginatedResponse<TeacherResponse> teacherResponsePaginatedResponse = new PaginatedResponse<>(
                teacherResponseList,
                paginatedModel.getPageData()
        );

        return new ResponseEntity<>(teacherResponsePaginatedResponse,HttpStatus.OK);
    }

    private void checkTeacher(TeacherModel teacherModel, HttpServletRequest request) {
            String token = jwtService.retrieveToken(request);
            Claims claims = jwtService.getClaimsFromToken(token);
            Integer userId = claims.get("user_id", Integer.class);
            if(!teacherModel.getPartner().getUserId().equals(userId)){
                throw new Unahautorized();
            }
    }
}
