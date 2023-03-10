package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import org.springframework.data.domain.PageRequest;

public interface PartecipationService {

    /**
     * Aggiunge la partecipazione di uno studente
     * @param studentPartecipationModel contiene i dati del corsoe della partecipazione dello studente
     * @return una singola Partecipazione dello Studente ad un Corso
     */
    StudentPartecipationModel add(StudentPartecipationModel studentPartecipationModel);

    /**
     * Recupera una singola Partecipazione tramite il Corso e lo Studente
     * @param studentPartecipationId id della partecipazione dello Studente
     * @param courseId id del Corso
     * @return una singola Partecipazione dello Studente
     */
    StudentPartecipationModel get(Integer courseId, Integer studentPartecipationId);

    /**
     * Recupero di tutte le Partecipazioni di tutti gli studenti per quel corso
     * @param courseId id del corso
     * @param pageRequest informazioni delle Paginazioni
     */
    PaginatedResponse<StudentPartecipationModel> getByCourse(Integer courseId, PageRequest pageRequest);

    /**
     * Recupera di tutte le Partecipazioni degli studenti
     * @param pageRequest contiene le informazioni della Paginazione
     */
    PaginatedResponse<StudentPartecipationModel> getAll(PageRequest pageRequest);

    /**
     * Aggiorna la Partecipazione dello Studente
     * @param studentPartecipationModifyModel contiene i dati della partecipazione modificata
     * @param studentId id della partecipazione
     * @param courseId id della partecipazione
     */
    StudentPartecipationModel update(StudentPartecipationModel studentPartecipationModifyModel, Integer studentId, Integer courseId);

    /**
     * Elimina una Partecipazione dello Studente
     * @param studentId id della Partecipazione dello Studente
     * @param courseId id della Partecipazione dello Studente
     */
    void delete(Integer studentId, Integer courseId);
}
