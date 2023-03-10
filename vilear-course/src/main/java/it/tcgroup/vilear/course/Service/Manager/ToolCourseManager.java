package it.tcgroup.vilear.course.Service.Manager;

import java.sql.Date;
import java.util.List;

import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.ToolCourseEntity;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ToolCourseManager {
    /**
     * Salva un determinato Tool associato al Corso
     * @param toolCourseModel contiene i dati del Tool
     */
    ToolCourseEntity save(ToolCourseModel toolCourseModel);

    /**
     * Recupero un singolo Tool associato al Corso
     * @param courseId id del corso
     * @param toolId id del tool associato al corso
     */
    ToolCourseEntity get(Integer toolId,Integer courseId);

    /**
     * Recupera Tutti i tool associati a quel corso
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Paginazione
     */
    Page<ToolCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera tutti i Tool associati al Corso
     * @param pageRequest contiene i dati della Paginazione
     */
    Page<ToolCourseEntity> getAll(PageRequest pageRequest);

    /**
     * Elimina il Tool associato al Tool
     * @param courseId id del Corso
     * @param toolId id del tool del Corso
     */
    void deleteToolCourse(Integer toolId,Integer courseId);

    /**
     * Recupera la quantità utilizzata in quelle determinate date
     * @param toolId id del tool
     * @param dates lista di date da verificare
     */
    List<Integer> getQuantity(Integer toolId, List<Date> dates);

    /**
     * Recupera il Tool da tutti i corsi e lo elimina
     * @param toolId id del Tool
     * @return
     */
    List<ToolCourseEntity> getByTool(Integer toolId);
}
