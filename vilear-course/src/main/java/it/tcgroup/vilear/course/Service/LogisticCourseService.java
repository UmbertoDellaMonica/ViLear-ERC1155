package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public interface LogisticCourseService {

    /**
     * Recupera una singola Logistica associata ad un Corso
     * @param courseId id del corso
     * @param logisticId id del Logistic
     * @return Logistica associata ad un corso
     */
    LogisticCourseModel get(Integer courseId, Integer logisticId);

    /**
     * Recupera tutta la Logistica per quel Corso
     * @param courseId id del corso
     * @param page numero di pagina
     * @param pageSize dimensione della pagina
     */
    PaginatedResponse<LogisticCourseModel>getByCourse(Integer courseId,Integer page,Integer pageSize);

    /**
     * Recupera tutta la Logistica associata ai corsi
     * @param logisticId  id della Logistica
     *                    che può essere associata ad una e più corsi
     */
    List<LogisticCourseModel>updateByLogistic(Integer logisticId);

    /**
     * Recupera tutta la Logistica associata ai corsi
     * @param pageRequest contiene i dati della Paginazione
     */
    PaginatedResponse<LogisticCourseModel> getAll(PageRequest pageRequest);

    /**
     * Aggiunge una Logistica associata ad un Corso
     * @param logisticCourseModel contiene i dati della Logistica associata al Corso
     * @return una singola Logistica associata ad un Corso
     */
    LogisticCourseModel add(LogisticCourseModel logisticCourseModel);

    /**
     * Aggiorna i dati della Logistica associata ad un corso
     * @param logisticCourseModifyModel contiene i dati della Logistica per il Corso
     * @param logisticId id della logistica
     * @param courseId id del corso
     * @return una Logistica per il Corso aggiornata
     */
    LogisticCourseModel update(LogisticCourseModel logisticCourseModifyModel, Integer logisticId, Integer courseId);

    /**
     * Elimina una Logistica associata ad un Corso
     * @param courseId id del corso
     * @param logisticId id della logistica
     */
    void deleteLogisticCourse(Integer courseId,Integer logisticId);

    boolean existsLogisticCourse(Integer logisticId, List<Date> dates);


}
