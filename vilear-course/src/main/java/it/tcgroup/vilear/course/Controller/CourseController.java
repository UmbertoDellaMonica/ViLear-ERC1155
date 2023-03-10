package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.base.Payload.Request.*;
import it.tcgroup.vilear.base.Payload.Response.*;
import it.tcgroup.vilear.course.Model.*;
import it.tcgroup.vilear.course.Service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    /**
     * Inserimento dati del Corso
     * @param courseRequest contiene i parametri e le informazioni del corso
     * @return un singolo corso aggiunto
     */
    @PostMapping(path = "")
    public ResponseEntity<CourseResponse>add(
        @RequestBody CourseRequest courseRequest
    ) {
        CourseModel courseModel = mapper.map(courseRequest, CourseModel.class);
        //Aggiunta del Corso
        courseModel = courseService.add(courseModel);
        CourseResponse courseResponse = mapper.map(courseModel,CourseResponse.class);

        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    /**
     * Recupera un singolo Corso
     * @param courseId id del corso
     */
    @GetMapping(path = "/{course_id}")
    public ResponseEntity<CourseResponse>get(
            @PathVariable(name = "course_id")Integer courseId
    ){
        CourseModel courseModel = courseService.get(courseId);
        CourseResponse courseResponse = mapper.map(courseModel,CourseResponse.class);
        return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }

}

