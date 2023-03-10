package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public interface ToolCourseService {

    /**
     * Inserimento dei dati di un Tool associato ad un Corso
     * @param toolCourseModel   contiene i dati  del Tool associati al Corso
     * @return un Tool associato ad un Corso nuovo
     */
    ToolCourseModel add(ToolCourseModel toolCourseModel);

    /**
     * Recupera un Tool associato ad un Corso
     * @param courseId id del corso
     * @param toolId id del Tool
     * @return un Tool associato ad un Corso
     */
    ToolCourseModel get(Integer courseId, Integer toolId);

    /**
     * Recupera tutta la Logistica per quel Corso
     * @param courseId id del Corso
     * @param pageRequest dimensione della Pagina
     */
    PaginatedResponse<ToolCourseModel> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera tutti i Tool del Corso
     * @param toolId id del Tool
     */
    List<ToolCourseModel> updateByTool(Integer toolId);

    /**
     * Recupera tutti Tool Associato al Corso
     * @param pageRequest contiene i dati della Paginazione
     */
    PaginatedResponse<ToolCourseModel> getAll(PageRequest pageRequest);

    /**
     * Aggiorna i dati di un Tool associato ad un Corso
     * @param toolCourseModifyModel contiene i dati del Tool associato al corso
     * @param toolId  contiene i dati del Tool
     * @return   un Tool associato ad un Corso Modificato
     */
    ToolCourseModel update(ToolCourseModel toolCourseModifyModel, Integer toolId, Integer courseId);

    /**
     * Elimina un Tool associato ad un Corso
     * @param courseId id del Corso
     * @param toolId id del Tool
     */
    void delete(Integer courseId, Integer toolId);

    /**
     * Mi restituisce la quantità che deve essere associata nel momento in cui :
     * Uno o più corsi abbiano la stessa Strumentazione
     * Differenziando le date in cui il Tool possa essere assegnato
     * Si effettua un Check sulla disponibilità del Tool che può essere assegnato
     * @param toolId id del Tool
     * @param dates  date del Corso
     */
    List<Integer> getSummaryQuantity(Integer toolId, List<Date> dates);

}
