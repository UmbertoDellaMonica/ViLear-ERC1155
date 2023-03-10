package it.tcgroup.vilear.course.Model;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
public class StudentPartecipationModel {

    private Integer personId;

    private CourseModel course;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Timestamp createdAt;

}
