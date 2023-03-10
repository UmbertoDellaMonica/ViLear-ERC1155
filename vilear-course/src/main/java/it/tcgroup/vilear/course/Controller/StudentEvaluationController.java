package it.tcgroup.vilear.course.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.StudentEvaluationResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student/evaluation")
public class StudentEvaluationController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    /**
     * Recupero della singola Valutazione dello Studente per quel singolo corso
     * @param evaluationId id della Valutaione
     * @param courseId id del Corso
     * @return StudentEvaluationResponse
     */
    @GetMapping(path = "/{evaluation_id}")
    public ResponseEntity<StudentEvaluationResponse> getEvaluationStudent(
            @PathVariable(name = "evaluation_id")Integer evaluationId,
            @RequestHeader(name = "course_id")Integer courseId,
            HttpServletRequest request
    ){
        // Recupero la Valutazione dello Studente
        StudentEvaluationModel studentEvaluationModel = studentService.getEvaluationStudent(evaluationId,courseId);
        checkEvaluation(studentEvaluationModel,request);

        // Mappo la Valutazione
        StudentEvaluationResponse studentEvaluationResponse=mapper.map(studentEvaluationModel, StudentEvaluationResponse.class);

        return new ResponseEntity<>(studentEvaluationResponse, HttpStatus.OK);
    }

    private void checkEvaluation(StudentEvaluationModel studentEvaluationModel, HttpServletRequest request) {
        String token = jwtService.retrieveToken(request);
        Claims claims = jwtService.getClaimsFromToken(token);
        Integer userId = claims.get("user_id", Integer.class);
        PersonResponse personResponse = vilearRegistrationServiceClient.getPerson(studentEvaluationModel.getStudentPartecipation().getPersonId());
        if(!personResponse.getUser().getId().equals(userId)){
            throw new Unahautorized();
        }
    }

}
