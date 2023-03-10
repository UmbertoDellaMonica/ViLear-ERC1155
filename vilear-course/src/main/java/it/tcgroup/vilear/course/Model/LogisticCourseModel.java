package it.tcgroup.vilear.course.Model;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
public class LogisticCourseModel {

    private Integer logisticId;

    private Integer partnerId;

    private CourseModel course;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    private Timestamp createdAt;


    public void setCourseId(Integer courseId) {
        if( course == null ) {
            course = new CourseModel();
        }
        course.setId(courseId);
    }
}
