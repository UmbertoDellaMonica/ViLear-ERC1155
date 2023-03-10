package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Request.StudentEvaluationRequest;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.StudentEvaluationResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Service.EvaluationService;
import it.tcgroup.vilear.course.Service.StudentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher/evaluation")
public class TeacherEvaluationController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EvaluationService evaluationService;

    /**
     * Aggiunge una valutazione di uno Studente per quel Corso
     * @param teacherCourseId id dell'associazione tra docente e corso
     * @param studentPartecipationId id dello Studente
     * @param studentEvaluationRequest contiene i dati per la valutazione dello studente
     * @return Valutazione dello Studente
     */
    @PostMapping(path = "")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<StudentEvaluationResponse> add(
        @RequestHeader(name = "teacher_course_id") Integer teacherCourseId,
        @RequestHeader(name = "course_id")Integer courseId,
        @RequestHeader(name = "student_partecipation_id") Integer studentPartecipationId,
        @RequestBody StudentEvaluationRequest studentEvaluationRequest
    ) {
        StudentEvaluationModel studentEvaluationModel = mapper.map(studentEvaluationRequest, StudentEvaluationModel.class);

        studentEvaluationModel.setTeacherCourseId(teacherCourseId);
        studentEvaluationModel.setStudentPartecipationId(studentPartecipationId);
        studentEvaluationModel.setCourseId(courseId);

        // Inserisci la Valutazione dello Studente
        studentEvaluationModel = studentService.addEvaluation(studentEvaluationModel);
        StudentEvaluationResponse studentEvaluationResponse = mapper.map(studentEvaluationModel,StudentEvaluationResponse.class);

        return new ResponseEntity<>(studentEvaluationResponse, HttpStatus.OK);
    }

    /**
     * Recupero della singola Valutazione dello Studente per quel singolo corso
     * @param evaluationId id della Valutaione
     * @param courseId id del Corso
     */
    @GetMapping(path = "/student/{evaluation_id}")
    public ResponseEntity<StudentEvaluationResponse>getEvaluation(
            @PathVariable(name = "evaluation_id")Integer evaluationId,
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestHeader(name = "teacher_id")Integer teacherId
    ){
        // Recupero la Valutazione dello Studente
        StudentEvaluationModel studentEvaluationModel = studentService.getEvaluationStudent(evaluationId,courseId);
        checkEvaluation(studentEvaluationModel,teacherId);
        // Mappo la Valutazione
        StudentEvaluationResponse studentEvaluationResponse=mapper.map(studentEvaluationModel, StudentEvaluationResponse.class);

        return new ResponseEntity<>(studentEvaluationResponse,HttpStatus.OK);
    }

    private void checkEvaluation(StudentEvaluationModel studentEvaluationModel, Integer teacherId) {
        if(!studentEvaluationModel.getTeacherCourse().getTeacherId().equals(teacherId)){
            throw new Unahautorized();
        }
    }

    /**
     * Aggiornamento della Valutazione di uno studente tramite :
     * @param courseId id del Corso
     * @param evaluationId id della valutazione
     * @param studentEvaluationRequest contiene i dati per la valutazione dello studente
     * @return Valutazione dello studente Aggiornata
     * */
    @PutMapping(path = "/{evaluation_id}")
    public ResponseEntity<StudentEvaluationResponse>edit(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "evaluation_id")Integer evaluationId,
            @RequestHeader(name = "teacher_id")Integer teacherId,
            @RequestBody StudentEvaluationRequest studentEvaluationRequest
    ){
        StudentEvaluationModel studentEvaluationModifyModel = mapper.map(studentEvaluationRequest,StudentEvaluationModel.class);
        checkEvaluation(studentService.getEvaluationStudent(evaluationId,courseId),teacherId);
        // Aggiornamento della Valutazione dello Studente
        studentEvaluationModifyModel = studentService.updateEvaluation(studentEvaluationModifyModel,evaluationId,courseId);

        StudentEvaluationResponse studentEvaluationResponse = mapper.map(studentEvaluationModifyModel, StudentEvaluationResponse.class);

        return new ResponseEntity<>(studentEvaluationResponse,HttpStatus.OK);
    }

    /**
     * Recupera tutte le Valutazioni degli Studenti per un corso in cui il Docente è associato
     * @param courseId id del Corso
     * @param teacherId id del Teacher associato al Corso
     * @param page visualizza la pagina richiesta
     * @param pageSize numero degl'elementi della pagina
     * @return una lista di Valutazioni associato ad un corso
     */
    @GetMapping(path = "/{teacher_id}")
    public ResponseEntity<PaginatedResponse<StudentEvaluationResponse>>filterEvaluationsByTeacherIdCourseId(
                                                                                @RequestHeader(name = "course_id")Integer courseId,
                                                                                @PathVariable(name = "teacher_id")Integer teacherId,
                                                                                @RequestParam(name = "page") int page,
                                                                                @RequestParam(name = "page_size") int pageSize){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<StudentEvaluationModel>studentEvaluationModelPaginatedResponse = evaluationService.getEvaluationByTeacherIdAndCourseId(courseId,teacherId,pageRequest);

        List<StudentEvaluationResponse>studentEvaluationResponseList = mapper.map(
                studentEvaluationModelPaginatedResponse.getData(),
                new TypeToken<List<StudentEvaluationResponse>>(){}.getType()
        );

        PaginatedResponse.PageData pageData = studentEvaluationModelPaginatedResponse.getPageData();

        PaginatedResponse<StudentEvaluationResponse>studentEvaluationResponsePaginatedResponse = new PaginatedResponse<StudentEvaluationResponse>();
        studentEvaluationResponsePaginatedResponse.setPageData(pageData);
        studentEvaluationResponsePaginatedResponse.setData(studentEvaluationResponseList);

        return new ResponseEntity<>(studentEvaluationResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Eliminazione della Valutazione dello Studente
     * @param evaluationId  id della Valuatazione
     * @param courseId  id del corso
     */
    @DeleteMapping(path = "/{evaluation_id}")
    public ResponseEntity<Boolean>delete(
            @RequestHeader(name = "course_id")Integer  courseId,
            @PathVariable(name = "evaluation_id")Integer evaluationId
    ){
        // Eliminazione di una Valutazione dello studente
        studentService.deleteEvalutation(evaluationId,courseId);
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

}
