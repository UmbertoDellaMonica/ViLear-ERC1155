package it.tcgroup.vilear.course.Model;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
public class ToolCourseModel {

    private Integer toolId;

    private Integer partnerId;

    private CourseModel course;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Integer quantity;

    private Timestamp createdAt;


    public void setCourseId(Integer courseId) {
        if( course == null ) {
            course = new CourseModel();
        }
        this.course.setId(courseId);
    }

}
