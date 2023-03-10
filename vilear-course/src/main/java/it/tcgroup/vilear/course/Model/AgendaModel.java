package it.tcgroup.vilear.course.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
public class AgendaModel {

    private Integer id;

    private Date courseDate;

    private Time timeBegin;

    private Time timeEnd;

    private Timestamp createdAt;

    private CourseModel course;
}
