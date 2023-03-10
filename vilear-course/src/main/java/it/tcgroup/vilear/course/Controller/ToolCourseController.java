package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Request.ToolCourseRequest;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolCourseResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import it.tcgroup.vilear.course.Service.CourseService;
import it.tcgroup.vilear.course.Service.ToolCourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolCourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ToolCourseService toolCourseService;

    /**
     * Inserimento dati di un Tool associato ad un Corso
     *
     * @param courseId             id del corso
     * @param toolCourseRequest    dettagli del tool
     * @return ToolCourseResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<ToolCourseResponse> addTool(
            @RequestHeader(name = "course_id") Integer courseId,
            @RequestBody ToolCourseRequest toolCourseRequest
    ) {
        ToolCourseModel toolCourseModel= mapper.map(toolCourseRequest, ToolCourseModel.class);
        toolCourseModel.setCourseId(courseId);

        // Aggiunta del Tool associato a quel Corso
        toolCourseModel = courseService.addTool(toolCourseModel);
        ToolCourseResponse toolCourseResponse = mapper.map(toolCourseModel, ToolCourseResponse.class);

        return new ResponseEntity<>(toolCourseResponse, HttpStatus.OK);
    }

    /**
     * Recupero del singolo Tool associato al Corso
     * @param courseId id del Corso
     * @param toolId id del tool
     */
    @GetMapping(path = "/{tool_id}")
    public ResponseEntity<ToolCourseResponse>get(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "tool_id")Integer toolId
    ){
        ToolCourseModel toolCourseModel = toolCourseService.get(courseId,toolId);
        ToolCourseResponse toolCourseResponse = mapper.map(toolCourseModel,ToolCourseResponse.class);

        return new ResponseEntity<>(toolCourseResponse,HttpStatus.OK);
    }

    /**
     * Recupera tutti i Tool  per quel Corso
     * @param courseId id del Corso
     */
    @GetMapping(path = "")
    public ResponseEntity<PaginatedResponse<ToolCourseResponse>>filterByCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestParam(name = "page")Integer page,
            @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<ToolCourseModel>paginatedResponse = toolCourseService.getByCourse(courseId,pageRequest);
        List<ToolCourseResponse>toolCourseResponseList = mapper.map(paginatedResponse.getData(),new TypeToken<List<ToolCourseResponse>>(){}.getType());
        PaginatedResponse.PageData pageData = paginatedResponse.getPageData();
        PaginatedResponse<ToolCourseResponse>logisticCourseResponsePaginatedResponse = new PaginatedResponse<>(toolCourseResponseList,pageData);

        return new ResponseEntity<>(logisticCourseResponsePaginatedResponse,HttpStatus.OK);
    }

    /**
     * Eliminazione di un Docente associato ad un Corso
     * @param courseId id del Corso
     * @param toolId id del Tool del Corso
     */
    @DeleteMapping(path = "/{tool_id}")
    public ResponseEntity<Boolean>deleteToolCourse(
            @RequestHeader(name = "course_id")Integer courseId,
            @PathVariable(name = "tool_id")Integer toolId
    ){
        // Eliminazione del Tool
        toolCourseService.delete(courseId,toolId);

        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }
}
