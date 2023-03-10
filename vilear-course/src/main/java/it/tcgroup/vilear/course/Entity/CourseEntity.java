package it.tcgroup.vilear.course.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name_courses",nullable = false)
    private String name;

    @Column(name = "date_publish",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datePublish;

    @Column(name = "start_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "persons_capacity",nullable = false)
    private Integer capacity;

    @Column(name = "active",nullable = false)
    private Boolean active;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<TeacherCourseEntity> teacherCourseList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<LogisticCourseEntity> logisticCourseList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<ToolCourseEntity> toolCourseList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<StudentPartecipationEntity> studentPartecipationList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<StudentEvaluationEntity> studentEvaluationList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<AgendaEntity>agendaList;

}
