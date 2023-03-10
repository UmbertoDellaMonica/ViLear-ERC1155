package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.course.Entity.AgendaEntity;
import it.tcgroup.vilear.course.Model.AgendaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AgendaManager {
    /**
     * Salva i dati dell'agenda
     * @param agendaModel contiene i dati dell'agenda
     */
    AgendaEntity save(AgendaModel agendaModel);

    /**
     * Recupera la singola Agenda
     * @param agendaId id dell'agenda
     * @param courseId id dell'agenda
     */
    AgendaEntity get(Integer agendaId, Integer courseId);

    /**
     * Recupera tutte le Date attraverso l'id del Corso
     * @param courseId id del Corso
     */
    Page<AgendaEntity> getByCourse(Integer courseId,PageRequest pageRequest);

    /**
     * Elimina l'agenda in questione
     *
     * @param agendaId id dell'agenda
     * @param courseId id del corso
     */
    void delete(Integer agendaId, Integer courseId);

    /**
     * Recupera l'agenda tramite :
     * -id del corso e dell'agenda
     * @param agendaId id dell'agenda
     * @param courseId id del corso
     */
    AgendaEntity getByAgendaAndCourse(Integer agendaId, Integer courseId);

    /**
     * Recupera tutte le date di tutti i corsi
     */
    Page<AgendaEntity> findAll(PageRequest pageRequest);
}
