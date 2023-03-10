package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import it.tcgroup.vilear.course.Service.LogisticCourseService;
import it.tcgroup.vilear.course.Service.TeacherCourseService;
import it.tcgroup.vilear.course.Service.ToolCourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/external")
public class ExternalCourseCallController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private LogisticCourseService logisticCourseService;
    @Autowired
    private ToolCourseService toolCourseService;


    @PutMapping("/tool")
    public ResponseEntity<Boolean>deleteTools(
            @RequestHeader(name = "tool_id")Integer toolId
    ){
        List<ToolCourseModel>toolCourseModelList = toolCourseService.updateByTool(toolId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping("/teacher")
    public ResponseEntity<Boolean>deleteTeachers(
            @RequestHeader(name = "teacher_id")Integer teacherId
    ){
        List<TeacherCourseModel>teacherCourseModelList = teacherCourseService.updateByTeacher(teacherId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping("/logistic")
    public ResponseEntity<Boolean>deleteLogistics(
            @RequestHeader(name = "logistic")Integer logisticId
    ){
        List<LogisticCourseModel>logisticCourseModelList = logisticCourseService.updateByLogistic(logisticId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}
