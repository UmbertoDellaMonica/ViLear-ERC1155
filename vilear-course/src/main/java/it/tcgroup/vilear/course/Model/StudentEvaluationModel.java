package it.tcgroup.vilear.course.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class StudentEvaluationModel {

    private Integer id;

    private TeacherCourseModel teacherCourse;

    private StudentPartecipationModel studentPartecipation;

    private CourseModel course;


    private Integer autonomy;

    private Integer initLevel;

    private Integer teamworkLevel;

    private Integer endLevel;

    private Timestamp createdAt;


    public void setTeacherCourseId(Integer teacherCourseId) {
        if( teacherCourse == null ) {
            teacherCourse = new TeacherCourseModel();
        }
        this.teacherCourse.setTeacherId(teacherCourseId);
    }

    public void setStudentPartecipationId(Integer studentPartecipationId) {
        if( studentPartecipation == null ) {
            studentPartecipation = new StudentPartecipationModel();
        }
        this.studentPartecipation.setPersonId(studentPartecipationId);
    }

    public void setCourseId(Integer courseId) {
        if( course == null ) {
            course = new CourseModel();
        }
        this.course.setId(courseId);
    }

}