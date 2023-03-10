package it.tcgroup.vilear.course.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CourseModel {

    private Integer id;

    private String name;

    private Date datePublish;

    private Date startDate;

    private Date endDate;

    private String description;

    private Integer capacity;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Timestamp createdAt;

    private List<AgendaModel> agendaList;

    private List<TeacherCourseModel> teacherCourseList;

    private List<LogisticCourseModel> logisticCourseList;

    private List<ToolCourseModel> toolCourseList;

    private List<StudentPartecipationModel> studentPartecipationList;

    private List<StudentEvaluationModel> studentEvaluationList;

}
