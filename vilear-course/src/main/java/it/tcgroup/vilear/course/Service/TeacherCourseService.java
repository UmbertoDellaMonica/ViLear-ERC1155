package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public interface TeacherCourseService {

    /**
     * Recupera un teacher associato al corso tramite :
     * @param teacherId id del teacher associato al corso
     * @param courseId id del corso
     * @return un Teacher associato al corso
     */
    TeacherCourseModel get(Integer teacherId,Integer courseId);

    /**
     * Aggiunge un Teacher al Corso
     * @param teacherCourseModel contiene i dati del Teacher
     * @return un teacher associato al corso
     */
    TeacherCourseModel add(TeacherCourseModel teacherCourseModel);

    /**
     * Recupera la Paginazione di tutti i Corsi
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Paginazione
     */
    PaginatedResponse<TeacherCourseModel> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupero tutta la Logistica di tutti i corsi
     * @param pageRequest contiene i dati della Paginazione
     */
    PaginatedResponse<TeacherCourseModel> getAll(PageRequest pageRequest);

    /**
     * Recupera tutti i teacher che sono definiti per quella logistica
     * @param teacherId
     */
    List<TeacherCourseModel> updateByTeacher(Integer teacherId);

    /**
     * Aggiornamento di un Teacher con un Corso
     * @param teacherCourseModifyModel contiene i dati del teacher associato al Corso
     * @param teacherId id del teacher associato al corso
     * @param courseId id del corso     */
    TeacherCourseModel update(TeacherCourseModel teacherCourseModifyModel, Integer teacherId,Integer courseId);

    /**
     * Elimina un Teacher associato ad un Corso
     * @param teacherCourseId id del Teacher
     * @param courseId id del Corso
     */
    void delete(Integer teacherCourseId, Integer courseId);

    /**
     * Verifica se esiste un Teacher che sia stato associato nelle date del Corso
     * Oppure se ne esiste già uno
     * @param id id del corso
     * @param agendaDays lista di tutte le date del corso
     */
    boolean exists(Integer id, List<Date> agendaDays);

}
