package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.StudentEvaluationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluationEntity, Integer> {

    @Query("SELECT s from StudentEvaluationEntity s where course_id=:courseId")
    Page<StudentEvaluationEntity> findEvaluationByCourse(
            @Param("courseId") Integer courseId,
            PageRequest pageRequest
    );

    @Query("SELECT s from StudentEvaluationEntity s where " +
            " course_id=:courseId AND " +
            " teacher_id=:teacherCourseId AND" +
            " student_id=:studentPartecipationId ")
    Optional<StudentEvaluationEntity> findEvaluation(
            @Param("courseId") Integer courseId,
            @Param("teacherCourseId") Integer teacherCourseId,
            @Param("studentPartecipationId")Integer studentPartecipationId
    );

    @Query("SELECT s from StudentEvaluationEntity s where " +
            "teacher_id=:teacherCourseId AND " +
            "student_id=:studentPartecipationId")
    List<StudentEvaluationEntity> filter(
            @Param("teacherCourseId") Integer teacherCourseId,
            @Param("studentPartecipationId") Integer studentPartecipationId
    );

    @Query("SELECT s from StudentEvaluationEntity s where " +
            "course_id=:courseId AND " +
            "teacher_id=:teacherId")
    Page<StudentEvaluationEntity> findByTeacherIdAndCourseId(
            @Param("courseId") Integer courseId,
            @Param("teacherId") Integer teacherId,
            PageRequest pageRequest
    );

    /**
     * Recupera tutte le valutazioni  degli studenti date un corso
     * @param courseId id del corso
     * @param pageRequest contiene le informazioni sulla Paginazione
     */
    @Query("SELECT s from StudentEvaluationEntity s where course_id=:courseId")
    Page<StudentEvaluationEntity> findByCourse(@Param("courseId") Integer courseId, PageRequest pageRequest);
}
