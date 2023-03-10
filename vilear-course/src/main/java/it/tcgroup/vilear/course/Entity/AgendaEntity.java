package it.tcgroup.vilear.course.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "agenda")
@Getter
@Setter
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "course_id",nullable = false)
    private CourseEntity course;

    @Column(name = "course_date",nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date courseDate;

    @Column(name = "time_begin",nullable = false)
    private Time timeBegin;

    @Column(name = "time_end",nullable = false)
    private Time timeEnd;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
