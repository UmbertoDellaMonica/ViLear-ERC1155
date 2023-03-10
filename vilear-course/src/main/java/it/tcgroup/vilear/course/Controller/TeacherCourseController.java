package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Request.TeacherCourseRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticCourseResponse;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherCourseResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Service.CourseService;
import it.tcgroup.vilear.course.Service.TeacherCourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherCourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    /**
     * Inserimento dei dati del Teacher associato ad un Corso
     * @param courseId id del corso
     * @return TeacherCourseResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<TeacherCourseResponse> add(
            @RequestHeader(name = "course_id") Integer courseId,
            @RequestBody TeacherCourseRequest teacherCourseRequest
    ) {
        // Aggiunta del Teacher associato a quel corso
        TeacherCourseModel teacherCourseModel = mapper.map(teacherCourseRequest, TeacherCourseModel.class);
        teacherCourseModel.setCourseId(courseId);
        teacherCourseModel = courseService.addTeacher(teacherCourseModel);
        TeacherCourseResponse teacherCourseResponse = mapper.map(teacherCourseModel, TeacherCourseResponse.class);

        return new ResponseEntity<>(teacherCourseResponse, HttpStatus.OK);
    }

    /**
     * Recupero del singolo Tool associato al Corso
     * @param teacherCourseId id del teacher associato al corso
     */
    @GetMapping(path = "/{teacher_course_id}")
    public ResponseEntity<TeacherCourseResponse>get(
            @PathVariable(name = "teacher_course_id")Integer teacherCourseId,
            @RequestHeader(name = "course_id")Integer courseId
    ){
        TeacherCourseModel teacherCourseModel = teacherCourseService.get(teacherCourseId,courseId);
        TeacherCourseResponse teacherCourseResponse = mapper.map(teacherCourseModel,TeacherCourseResponse.class);

        return new ResponseEntity<>(teacherCourseResponse,HttpStatus.OK);
    }

    /**
     * Recupera tutti i docenti per quel Corso
     * @param courseId id del Corso
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<TeacherCourseResponse>>filterByCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<TeacherCourseModel>paginatedResponse = teacherCourseService.getByCourse(courseId,pageRequest);
        List<TeacherCourseResponse>teacherCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<TeacherCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();
        PaginatedResponse<TeacherCourseResponse>teacherLogisticCourseResponsePaginatedResponse =new PaginatedResponse<>(teacherCourseResponseList,pageData);

        return new ResponseEntity<>(teacherLogisticCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Eliminazione di un Docente associato ad un Corso
     * @param courseId id del Corso
     * @param teacherCourseId id del Teacher del Corso
     */
    @DeleteMapping(path = "/{teacher_course_id}")
    public ResponseEntity<Boolean>delete(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "teacher_course_id")Integer teacherCourseId
    ){
        // Eliminazione del Teacher
        teacherCourseService.delete(teacherCourseId,courseId);

        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }
}
