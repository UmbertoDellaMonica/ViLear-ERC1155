package it.tcgroup.vilear.course.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "students_evaluation")
@Getter
@Setter
public class StudentEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(value = "course_id", referencedColumnName = "course_id"))
    })
    private TeacherCourseEntity teacherCourse;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "student_id", referencedColumnName = "person_id", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(value = "course_id", referencedColumnName = "course_id"))
    })
    private StudentPartecipationEntity studentPartecipation;

    @Column(name = "autonomy",nullable = false)
    private Integer autonomy;

    @Column(name = "init_level",nullable = false)
    private Integer initLevel;

    @Column(name = "teamwork_level",nullable = false)
    private Integer teamworkLevel;

    @Column(name = "end_level",nullable = false)
    private Integer endLevel;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
