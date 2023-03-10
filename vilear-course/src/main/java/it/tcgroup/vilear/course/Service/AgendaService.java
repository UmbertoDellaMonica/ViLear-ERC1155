package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.AgendaModel;
import it.tcgroup.vilear.course.Model.CourseModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AgendaService {

    /**
     * Aggiunta dei dati dell'Agenda associata ad un Corso
     * @param agendaModel contiene dati dell'Agenda
     * @return i dati inseriti dell'agenda
     */
    AgendaModel add(AgendaModel agendaModel);

    /**
     * Recupero di una singola Agenda
     * @param agendaId id dell'agenda
     * @param courseId id del corso
     * @return una singola Agenda associata ad un Corso
     */
    AgendaModel get(Integer agendaId, Integer courseId);

    /**
     * Recupera tutta la lista delle Agende
     * @param courseId    id del corso
     * @param pageRequest contiene i dati della Pagina
     * @return una Lista di Agenda dall'id del Corso
     */
    PaginatedResponse<AgendaModel> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupero di tutte le Agende
     * @param pageRequest  contiene tutti i dati della Paginazione
     */
    PaginatedResponse<AgendaModel> getAll(
            PageRequest pageRequest
    );


    /**
     * Aggiornamento dei dati dell'Agenda
     *
     * @param agendaModifyModel contiene i dati dell'agenda
     * @param agendaId          id dell'agenda
     * @param courseId
     * @return L'agenda aggiornata
     */
    AgendaModel update(AgendaModel agendaModifyModel, Integer agendaId, Integer courseId);

    /**
     * Elimina una singola agenda
     *
     * @param agendaId id dell'agenda
     * @param courseId
     */
    void delete(Integer agendaId,Integer courseId);

    /**
     * Inserisce tramite la data di inizio e la data di fine tutti i dati dell'agenda
     * verifica se questa è una data di festività oppure
     * è sabato/domenica o
     * Pasqua/Pasquetta
     * @param courseModel dati del corso
     * @param agendaModel dati dell'agenda
     */
    List<AgendaModel> calculate(CourseModel courseModel, AgendaModel agendaModel);


}
