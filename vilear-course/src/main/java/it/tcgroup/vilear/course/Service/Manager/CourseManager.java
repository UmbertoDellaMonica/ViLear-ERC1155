package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.course.Entity.CourseEntity;
import it.tcgroup.vilear.course.Model.CourseModel;

public interface CourseManager {

    /**
     * Salva i dati del Corso
     * @param courseModel contiene i dati del Corso
     * @return un singolo Corso
     */
    CourseEntity save(CourseModel courseModel);

    /**
     * Recupera i dati del singolo Corso
     * @param courseId id del corso
     */
    CourseEntity get(Integer courseId);

    /**
     * Elimina il corso (soft-delete)
     * @param courseId id del corso
     */
    void delete(Integer courseId);
}
