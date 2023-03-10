package it.tcgroup.vilear.course.Controller;

import io.jsonwebtoken.Claims;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.StudentPartecipationResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import it.tcgroup.vilear.course.Repository.StudentPartecipationRepository;
import it.tcgroup.vilear.course.Service.PartecipationService;
import it.tcgroup.vilear.course.Service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentPartecipationRepository studentPartecipationRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PartecipationService partecipationService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    @Autowired
    private JwtService jwtService;


    /**
     * Inserimento della candidatura di uno Studente
     * @param personId id dello Studente
     * @param courseId id del Corso
     * @return StudentPartecipationResponse
     */
    @PostMapping(path = "/candidate")
    public ResponseEntity<StudentPartecipationResponse>candidate(
            @RequestHeader(name = "person_id")Integer personId,
            @RequestHeader(name = "course_id")Integer courseId
    ){
        // Candidatura dello studente per quel corso
        StudentPartecipationModel studentPartecipationModel = studentService.candidate(courseId,personId);
        StudentPartecipationResponse studentPartecipationResponse = mapper.map(studentPartecipationModel,StudentPartecipationResponse.class);

        return new ResponseEntity<>(studentPartecipationResponse, HttpStatus.OK);
    }


    /**
     * Recupero di una singola Partecipazione dello Studente
     * @param courseId id del Corso
     * @param studentPartecipationId id della partecipazione dello studente
     * @return una singola Partecipazione dello Studente
     */
    @GetMapping(path = "/partecipation/{partecipation_id}")
    public ResponseEntity<StudentPartecipationResponse>getStudentPartecipation(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "partecipation_id")Integer studentPartecipationId,
            HttpServletRequest request
    ){
        // Recupero di un singolo Partecipazione dello Studente
        StudentPartecipationModel studentPartecipationModel = partecipationService.get(courseId,studentPartecipationId);
        checkPartecipation(studentPartecipationModel,request);
        StudentPartecipationResponse studentPartecipationResponse = mapper.map(studentPartecipationModel, StudentPartecipationResponse.class);
        // Mi prendo i dati dalla persona
        return new ResponseEntity<>(studentPartecipationResponse,HttpStatus.OK);
    }

    private void checkPartecipation(StudentPartecipationModel studentPartecipationModel, HttpServletRequest request) {
        String token = jwtService.retrieveToken(request);
        Claims claims = jwtService.getClaimsFromToken(token);
        Integer userId = claims.get("user_id", Integer.class);
        PersonResponse personResponse = vilearRegistrationServiceClient.getPerson(studentPartecipationModel.getPersonId());
        if(!personResponse.getUser().getId().equals(userId)){
            throw new Unahautorized();
        }
    }

}
