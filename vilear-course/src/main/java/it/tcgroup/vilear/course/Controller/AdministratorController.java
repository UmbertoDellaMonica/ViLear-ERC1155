package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Request.AgendaRequest;
import it.tcgroup.vilear.base.Payload.Response.*;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Model.*;
import it.tcgroup.vilear.course.Service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class AdministratorController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ToolCourseService toolCourseService;

    @Autowired
    private LogisticCourseService logisticCourseService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private PartecipationService partecipationService;


    /**
     * Inserisce i dati dell'agenda tra la data che inizia e la data immessa
     * Esegue vari controlli
     * Controlla la data odierna se è festivo oppure no
     * Controlla se la data è domenica oppure sabato
     * Controlla se è Pasquetta
     *
     * Mi setta tutte le date di quel determinato periodo di tempo
     * @param courseId id del Corso
     */
    @PostMapping(path = "/agenda/calculate")
    public ResponseEntity<List<AgendaResponse>> calculate(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestBody AgendaRequest agendaRequest
    ){
        CourseModel courseModel = courseService.get(courseId);
        AgendaModel agendaModel = mapper.map(agendaRequest,AgendaModel.class);
        // Calcola il numero dei giorni che non sono festivi e li restituiamo come risposta
        List<AgendaModel>agendaModelList = agendaService.calculate(courseModel,agendaModel);
        List<AgendaResponse>agendaResponseList = mapper.map(agendaModelList,new TypeToken<List<AgendaResponse>>(){}.getType());
        return new ResponseEntity<>(agendaResponseList,HttpStatus.OK);
    }


    /**
     * Elimina il corso rendendolo inattivo
     * @param courseId id del corso
     */
    @DeleteMapping(path = "/{course_id}")
    public ResponseEntity<Boolean>deleteCourse(
            @PathVariable(name = "course_id")Integer courseId
    ){
        courseService.delete(courseId);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    //--------------------------- CHOOSE PATH ------------------//

    /**
     * Approvazione/Rifiuto del Corso
     * @param courseId id del corso
     * @param decision approvazione/rifiuto
     * @return un Corso Aggiornato
     */
    @PutMapping(path = "/{course_id}/choose")
    public ResponseEntity<CourseResponse>chooseCourse(
            @PathVariable(name = "course_id")Integer courseId,
            @RequestParam("decision") DecisionEnum decision
    ){
        // Aggiornamento del Corso
        CourseModel courseModel = courseService.setStatusCourse(courseId,decision);
        CourseResponse courseResponse = mapper.map(courseModel,CourseResponse.class);

        return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }

    /**
     * Approvazione/Rifiuto di un Teacher associato ad un Corso
     * @param courseId si riferisce all'id del corso
     * @param decision si riferisce ad un determinato
     * @param teacherCourseId si riferisce ad un id del teacher associato al corso
     * @return TeacherCourseResponse
     */
    @PutMapping(path = "/choose/{teacher_id}/teacher")
    public ResponseEntity<TeacherCourseResponse>chooseTeacherCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "teacher_id")Integer teacherCourseId,
            @RequestParam(name = "decision")DecisionEnum decision
    ){
        // Aggiornamento dello Stato del TeacherCourse
        TeacherCourseModel teacherCourseModel = courseService.setStatusTeacherCourse(courseId,teacherCourseId,decision);
        TeacherCourseResponse teacherCourseResponse = mapper.map(teacherCourseModel,TeacherCourseResponse.class);

        return new ResponseEntity<>(teacherCourseResponse,HttpStatus.OK);
    }

    /**
     * Approvazione / Rifiuto del Tool associato ad un Corso
     * @param courseId id del corso
     * @param decision approvazione/rifiuto
     * @param toolId  id del Tool
     * @return ToolCourseResponse
     */
    @PutMapping(path = "/choose/{tool_id}/tool")
    public ResponseEntity<ToolCourseResponse>chooseToolCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "tool_id")Integer toolId,
            @RequestParam(name = "decision")DecisionEnum decision
    ){
        // Aggiornamento dello stato del ToolCourse
        ToolCourseModel toolCourseModel = courseService.setStatusToolCourse(courseId,toolId,decision);

        ToolCourseResponse toolCourseResponse = mapper.map(toolCourseModel,ToolCourseResponse.class);

        return new ResponseEntity<>(toolCourseResponse,HttpStatus.OK);
    }

    /**
     * Approvazione/Rifiuto della Logistica associata a quel Corso
     * @param courseId id del corso
     * @param decision approvazione/rifiuto
     * @param logisticId id della logistica
     * @return LogisticCourseResponse
     */
    @PutMapping(path = "/choose/{logistic_id}/logistic")
    public ResponseEntity<LogisticCourseResponse>chooseLogisticCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "logistic_id")Integer logisticId,
            @RequestParam(name = "decision")DecisionEnum decision
    ){
        // Aggiornamento della Logistica per quel Corso
        LogisticCourseModel logisticCourseModel = courseService.setStatusLogisticCourse(courseId,logisticId,decision);
        LogisticCourseResponse logisticCourseResponse = mapper.map(logisticCourseModel,LogisticCourseResponse.class);

        return new ResponseEntity<>(logisticCourseResponse,HttpStatus.OK);
    }

    /**
     * Approvazione/Rifiuto Candidatura
     * @param studentPartecipationId id della partecipazione dello studente
     * @param decision Decisione in merito alla candidatura
     * @return Partecipazione dello Studente aggiornato
     */
    @PutMapping(path = "/student/{student_partecipation_id}/choose")
    public ResponseEntity<StudentPartecipationResponse>chooseStudentPartecipation(
            @PathVariable(name = "student_partecipation_id")Integer studentPartecipationId,
            @RequestParam(name = "decision") DecisionEnum decision,
            @RequestHeader(name = "course_id")Integer courseId
    ){
        StudentPartecipationModel studentPartecipationModel = studentService.choose(studentPartecipationId,courseId,decision);
        StudentPartecipationResponse studentPartecipationResponse = mapper.map(studentPartecipationModel,StudentPartecipationResponse.class);
        return new ResponseEntity<>(studentPartecipationResponse,HttpStatus.OK);
    }

    //------------------------- GET ALL MAPPER ----------------------//

    /**
     * Recupera tutte le agende di tutti i corsi
     * @param page numero di Pagina
     * @param pageSize dimensione della pagina
     */
    @GetMapping(path = "/agenda")
    public ResponseEntity<PaginatedResponse<AgendaResponse>>filterAgenda(
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<AgendaModel>paginatedResponse = agendaService.getAll(pageRequest);
        List<AgendaResponse>agendaResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<AgendaResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<AgendaResponse>agendaResponsePaginatedResponse = new PaginatedResponse<>(agendaResponseList,pageData);
        return new ResponseEntity<>(agendaResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutti i tool di tutti i corsi
     * @param page numero della Pagina
     * @param pageSize dimensione della Pagina
     */
    @GetMapping(path = "/tool")
    public ResponseEntity<PaginatedResponse<ToolCourseResponse>>filterToolCourse(
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<ToolCourseModel>paginatedResponse = toolCourseService.getAll(pageRequest);
        List<ToolCourseResponse>toolCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<ToolCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<ToolCourseResponse>toolCourseResponsePaginatedResponse = new PaginatedResponse<>(toolCourseResponseList,pageData);
        return new ResponseEntity<>(toolCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutta la Logistica dei Corsi
     * @param page numero della Pagina
     * @param pageSize dimensione della Pagina
     */
    @GetMapping(path = "/logistic")
    public ResponseEntity<PaginatedResponse<LogisticCourseResponse>>filterLogisticCourse(
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<LogisticCourseModel>paginatedResponse = logisticCourseService.getAll(pageRequest);
        List<LogisticCourseResponse>logisticCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<LogisticCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<LogisticCourseResponse>logisticCourseResponsePaginatedResponse =new PaginatedResponse<>(logisticCourseResponseList,pageData);
        return new ResponseEntity<>(logisticCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutti i Teacher associati ai Corsi
     * @param page numero della Pagina
     * @param pageSize dimensione della pagina
     */
    @GetMapping(path = "/teacher")
    public ResponseEntity<PaginatedResponse<TeacherCourseResponse>>filterTeacherCourse(
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<TeacherCourseModel>paginatedResponse = teacherCourseService.getAll(pageRequest);
        List<TeacherCourseResponse>teacherCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<TeacherCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<TeacherCourseResponse>teacherCourseResponsePaginatedResponse = new PaginatedResponse<>(teacherCourseResponseList,pageData);
        return new ResponseEntity<>(teacherCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutte le Partecipazioni di uno studente relative ad un corso
     * @param page visualizza la pagina richiesta
     * @param pageSize numero degl'elementi della pagina
     * @return
     */
    @GetMapping(path = "/student/partecipation/all")
    public ResponseEntity<PaginatedResponse<StudentPartecipationResponse>>filterStudentPartecipationsByCourse(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page_size") int pageSize
    ){
        // Recupero di tutte le Partecipazioni degli studenti
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        PaginatedResponse<StudentPartecipationModel>paginatedResponse = partecipationService.getAll(pageRequest);
        List<StudentPartecipationResponse>studentPartecipationResponsesList = mapper.map(paginatedResponse.getData(),
                new TypeToken<List<StudentPartecipationResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();
        PaginatedResponse<StudentPartecipationResponse>partecipationResponsePaginatedResponse = new PaginatedResponse<>(studentPartecipationResponsesList,pageData);
        return new ResponseEntity<>(partecipationResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutte le Valutazioni di uno studente tramite
     * @param page visualizza la pagina richiesta
     * @param pageSize numero degl'elementi della pagina
     */
    @GetMapping(path = "/student/evaluation/all")
    public ResponseEntity<PaginatedResponse<StudentEvaluationResponse>>filterStudentEvaluationsByCourse(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page_size") int pageSize
    ){
        // Recupero della Paginazione relativa alla Valutazione dello Studente
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        PaginatedResponse<StudentEvaluationModel>paginatedResponse = evaluationService.getAll(pageRequest);
        List<StudentEvaluationResponse>studentEvaluationResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<StudentEvaluationResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<StudentEvaluationResponse>studentEvaluationResponsePaginatedResponse=new PaginatedResponse<>(studentEvaluationResponseList,pageData);


        return new ResponseEntity<>(studentEvaluationResponsePaginatedResponse,HttpStatus.OK);
    }



    /**
     * Recupero di tutte le Partecipazioni di uno studente relative ad un corso
     * @param courseId id del Corso
     * @param page visualizza la pagina richiesta
     * @param pageSize numero degl'elementi della pagina
     * @return
     */
    @GetMapping(path = "/student/partecipation")
    public ResponseEntity<PaginatedResponse<StudentPartecipationResponse>>filterStudentPartecipationsByCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page_size") int pageSize
    ){
        // Recupero di tutte le Partecipazioni degli studenti
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        PaginatedResponse<StudentPartecipationModel>paginatedResponse = partecipationService.getByCourse(courseId,pageRequest);
        List<StudentPartecipationResponse>studentPartecipationResponsesList = mapper.map(paginatedResponse.getData(),
                new TypeToken<List<StudentPartecipationResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();
        PaginatedResponse<StudentPartecipationResponse>partecipationResponsePaginatedResponse = new PaginatedResponse<>(studentPartecipationResponsesList,pageData);
        return new ResponseEntity<>(partecipationResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Recupero di tutte le Valutazioni di uno studente tramite
     * @param courseId id del Corso
     * @param page visualizza la pagina richiesta
     * @param pageSize numero degl'elementi della pagina
     */
    @GetMapping(path = "/student/evaluation")
    public ResponseEntity<PaginatedResponse<StudentEvaluationResponse>>filterStudentEvaluationsByCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page_size") int pageSize
    ){
        // Recupero della Paginazione relativa alla Valutazione dello Studente
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        PaginatedResponse<StudentEvaluationModel>paginatedResponse = evaluationService.getByCourse(courseId,pageRequest);
        List<StudentEvaluationResponse>studentEvaluationResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<StudentEvaluationResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();

        PaginatedResponse<StudentEvaluationResponse>studentEvaluationResponsePaginatedResponse=new PaginatedResponse<>(studentEvaluationResponseList,pageData);


        return new ResponseEntity<>(studentEvaluationResponsePaginatedResponse,HttpStatus.OK);
    }

}
