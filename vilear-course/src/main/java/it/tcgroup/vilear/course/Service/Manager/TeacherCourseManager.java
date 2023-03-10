package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public interface TeacherCourseManager {
    /**
     * Aggiunge un docente
     * @param teacherCourseModel contiene i dati del Teacher
     */
    TeacherCourseEntity save(TeacherCourseModel teacherCourseModel);

    /**
     * Recupera i dati del Docente associato al Corso
     * @param teacherId id del Teacher
     * @param courseId id del corso
     */
    TeacherCourseEntity get(Integer teacherId,Integer courseId);

    /**
     * Recupera tutti Docenti associati a quel corso
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Paginazione
     */
    Page<TeacherCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera tutti Docenti dei corsi
     * @param pageRequest contiene i dati sulla Paginazione
     */
    Page<TeacherCourseEntity> getAll(PageRequest pageRequest);

    /**
     * Elimina il Docente associato al Corso
     * @param teacherCourseId id del Teacher associato al Corso
     * @param courseId id del corso
     */
    void deleteTeacherCourse(Integer teacherCourseId, Integer courseId);

    /**
     * Verifica se quel Teacher sia stato già associato in date contenute nell'agenda del corso
     * Oppure se ve ne è già uno
     * @param teacherPersonId id del Teacher
     * @param days lista di tutte le date
     */
    boolean existsTeacherCourse(Integer teacherPersonId, List<Date> days);


    /**
     * Recupera tutti i Teacher associati al Corso tramite l'id del Teacher
     * @param teacherId teacher id
     */
    List<TeacherCourseEntity> getByTeacher(Integer teacherId);
}
