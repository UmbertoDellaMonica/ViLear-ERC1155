package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Request.AgendaRequest;
import it.tcgroup.vilear.base.Payload.Response.AgendaResponse;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Model.AgendaModel;
import it.tcgroup.vilear.course.Service.AgendaService;
import it.tcgroup.vilear.course.Service.CourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/agenda")
public class AgendaController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private CourseService courseService;

    /**
     * Inserimento dei dati dell'agenda relativa al Corso
     * @param courseId id del corso
     * @param agendaRequest contiene i dati di dati corso e poi anche l'orario di inizio e l'orario di fine
     * @return AgendaResponse
     */
    @PostMapping(path = "")
    public ResponseEntity<AgendaResponse>add(
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestBody AgendaRequest agendaRequest
    ){
        AgendaModel agendaModel=mapper.map(agendaRequest,AgendaModel.class);
        // Aggiunta dei dati dell'agenda
        agendaModel = courseService.addAgenda(courseId,agendaModel);
        AgendaResponse agendaResponse=mapper.map(agendaModel,AgendaResponse.class);

        return new ResponseEntity<>(agendaResponse,HttpStatus.OK);
    }

    /**
     * Recupera una singola data in agenda
     * @param agendaId id dell'agenda
     */
    @GetMapping("/{agenda_id}")
    public ResponseEntity<AgendaResponse> get(
            @PathVariable(name = "agenda_id")Integer agendaId,
            @RequestHeader(name = "course_id")Integer courseId
    ){
        AgendaModel agendaModel = agendaService.get(agendaId,courseId);
        AgendaResponse agendaResponse = mapper.map(agendaModel,AgendaResponse.class);
        return new ResponseEntity<>(agendaResponse,HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<PaginatedResponse<AgendaResponse>>filterByCourse(
        @RequestHeader(name = "course_id")Integer courseId ,
        @RequestParam(name = "page")Integer page,
        @RequestParam(name = "page_size")Integer pageSize
    ){
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        PaginatedResponse<AgendaModel>paginatedResponse = agendaService.getByCourse(courseId,pageRequest);
        PaginatedResponse<AgendaResponse>agendaResponsePaginatedResponse = mapper.map(paginatedResponse,new TypeToken<PaginatedResponse<AgendaResponse>>(){}.getType());
        return new ResponseEntity<>(agendaResponsePaginatedResponse,HttpStatus.OK);
    }

}
