package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.LogisticCourseEntity;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public interface LogisticCourseManager {
    /**
     * Aggiunge una Logistica associata a quel Corso
     * @param logisticCourseModel contiene i dati della Logistica
     */
    LogisticCourseEntity save(LogisticCourseModel logisticCourseModel);

    /**
     * Verifica che la Logistica esista oppure no
     * @param logisticId id della Logistica
     * @param dates una lista di date che posso verificare la sua esistenza
     */
    boolean existsLogisticCourse(Integer logisticId, List<Date> dates);

    /**
     * Recupera la singola Logistica associata al Corso
     * @param logisticId id della Logistica associato al Corso
     * @param courseId id della Logistica associato al Corso
     */
    LogisticCourseEntity get(Integer logisticId,Integer courseId);

    /**
     * Recupera tutte le Pagine con la Logistica associata al Corso
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Pagina
     */
    Page<LogisticCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera tutta la Logistica con la Paginazione
     * @param pageRequest contiene i dati della Paginazione
     */
    Page<LogisticCourseEntity> getAll(PageRequest pageRequest);

    /**
     * Elimina la logistica associata al Corso
     * @param logisticId id della Logistica associata al Corso
     * @param courseId id della Logistica associata al Corso
     */
    void deleteLogisticCourse(Integer logisticId,Integer courseId);


    /**
     * Recupera tutta la Logistica dei corsi attraverso l'id dei corsi
     * @param logisticId
     */
    List<LogisticCourseEntity> getByLogistic(Integer logisticId);
}
