package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.AgendaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity,Integer> {

    /**
     * Recupera la singola Agenda tramite
     * @param agendaId id dell'agenda
     * @param courseId id del corso
     * @return NULL se non esiste, se esiste
     */
    @Query("SELECT a from AgendaEntity a where id=:agendaId AND course_id=:courseId")
    Optional<AgendaEntity> findByAgendaIdAndCourseId(Integer agendaId, Integer courseId);

    /**
     * Recupera tutte le Date delle Agende
     * @param courseId id del corso
     * @param pageRequest contiene le info sulla Paginazione
     */
    @Query("SELECT a from AgendaEntity a where course_id=:courseId")
    Page<AgendaEntity> findByCourse(Integer courseId, PageRequest pageRequest);

}
