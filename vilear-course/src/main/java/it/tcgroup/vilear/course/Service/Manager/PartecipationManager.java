package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.course.Entity.StudentPartecipationEntity;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PartecipationManager {

    /**
     * Salva i dati della partecipazione dello studente
     * @param studentPartecipationModel contiene i dati della partecipazione dello studente
     * @return una partecipazione dello studente
     */
    StudentPartecipationEntity save(StudentPartecipationModel studentPartecipationModel);


    /**
     * Recupera la singola Partecipazione dello studente
     * @param courseId id del corso
     * @param studentId id della Partecipazione
     */
    StudentPartecipationEntity get(Integer courseId, Integer studentId);

    /**
     * Elimina la partecipazione dello Studente
     * @param studentId id della Partecipazione dello studente
     * @param courseId id della Partecipazione dello studente
     */
    void delete(Integer studentId, Integer courseId);

    /**
     * Recupero delle Partecipazioni degli studenti per quel corso
     * @param courseId id del corso
     * @param pageRequest contiene le informazioni della Paginazione
     */
    Page<StudentPartecipationEntity> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera tutte le Partecipazioni degli studenti
     * @param pageRequest contiene le info della Paginazione
     */
    Page<StudentPartecipationEntity> getAll(PageRequest pageRequest);
}
