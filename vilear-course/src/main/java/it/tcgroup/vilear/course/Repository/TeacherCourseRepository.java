package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourseEntity,TeacherCourseEntity.PrimaryKey> {


    /**
     * Recupera tutti i docenti che sono stati associati ad un corso
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Paginazione
     */
    @Query("SELECT t from TeacherCourseEntity t where course_id=:courseId")
    Page<TeacherCourseEntity> findByCourse(@Param("courseId") Integer courseId, PageRequest pageRequest);

    @Query("SELECT t from TeacherCourseEntity t where teacher_id=:teacherId")
    List<TeacherCourseEntity> findById(@Param("teacherId") Integer teacherId);

    /**
     * Verifica se esiste un Docente per quel corso in quelle determinate date
     * @param teacherPersonId id del Teacher
     * @param dates lista di Date
     */
    @Query(value = "select " +
            " case " +
            " when count(tc.*)>0 " +
            " then " +
            "   true  " +
            "else " +
            " false end" +
            " from " +
            " teachers_courses tc, courses c ,agenda a " +
            " where " +
            " c.id = a.course_id and " +
            " tc.course_id = c.id and "+
            " tc.teacher_id =:teacherPersonId and " +
            " a.course_date  in :dates" +
            " group by a.course_date ;",nativeQuery = true)
    Boolean existsTeacherCourse(
            @Param("teacherPersonId")Integer teacherPersonId,
            @Param("dates") List<Date> dates
    );



}
