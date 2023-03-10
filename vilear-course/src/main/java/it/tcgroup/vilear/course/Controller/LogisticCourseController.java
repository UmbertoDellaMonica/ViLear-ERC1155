package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Request.LogisticCourseRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticCourseResponse;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Service.CourseService;
import it.tcgroup.vilear.course.Service.LogisticCourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logistic")
public class LogisticCourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LogisticCourseService logisticCourseService;

    /**
     * Inserimento dei dati della Logistica associata al Corso
     * @param courseId id del corso
     * @return LogisticCourseResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<LogisticCourseResponse> add(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestBody LogisticCourseRequest logisticCourseRequest
    ) {
        LogisticCourseModel logisticCourseModel = mapper.map(logisticCourseRequest,LogisticCourseModel.class);
        logisticCourseModel.setCourseId(courseId);
        // Inserimento dei dati della Logistica
        logisticCourseModel = courseService.addLogistic(logisticCourseModel);
        LogisticCourseResponse logisticCourseResponse = mapper.map(logisticCourseModel, LogisticCourseResponse.class);

        return new ResponseEntity<>(logisticCourseResponse, HttpStatus.OK);
    }

    /**
     * Recupero della singola Logistica associata al Corso
     * @param courseId id del Corso
     * @param logisticId id della Logistica
     */
    @GetMapping(path = "/{logistic_id}")
    public ResponseEntity<LogisticCourseResponse>get(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "logistic_id")Integer logisticId
    ){
        // Recupera l'id della Logistica
        LogisticCourseModel logisticCourseModel = logisticCourseService.get(courseId,logisticId);
        LogisticCourseResponse logisticCourseResponse = mapper.map(logisticCourseModel,LogisticCourseResponse.class);

        return new ResponseEntity<>(logisticCourseResponse,HttpStatus.OK);
    }

    /**
     * Recupera tutta la Logistica per quel Corso
     * @param courseId id del Corso
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<LogisticCourseResponse>>filterByCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PaginatedResponse<LogisticCourseModel>paginatedResponse = logisticCourseService.getByCourse(courseId,page,pageSize);
        List<LogisticCourseResponse> logisticCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<LogisticCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();
        PaginatedResponse<LogisticCourseResponse>logisticCourseResponsePaginatedResponse = new PaginatedResponse<>(logisticCourseResponseList,pageData);

        return new ResponseEntity<>(logisticCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Eliminazione della Logistica associata ad un corso
     * @param courseId id del Corso
     * @param logisticId id della logistica
     */
    @DeleteMapping(path = "/{logistic_id}")
    public ResponseEntity<Boolean>delete(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "logistic_id")Integer logisticId
    ){
        // Eliminazione della logistica
        logisticCourseService.deleteLogisticCourse(courseId,logisticId);

        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

}
