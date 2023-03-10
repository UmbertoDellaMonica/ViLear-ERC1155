package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import org.springframework.data.domain.PageRequest;

public interface EvaluationService {

    /**
     * Aggiunge una Valutazione per lo studente
     * @param studentEvaluationModel contiene i dati della valutazione dello studente
     * @return una Valutazione di uno studente
     */
    StudentEvaluationModel add(StudentEvaluationModel studentEvaluationModel);

    /**
     * Recupera una singola valutazione dello studente
     * @param evaluationId id della Valutazione
     * @param courseId id del Corso
     */
    StudentEvaluationModel get(Integer evaluationId, Integer courseId);


    /**
     * Recupero di tutte le Valutazioni degli studenti di quel corso
     * Attraverso l'id del Teacher e l'id del Corso
     *
     * @param courseId    id del Corso
     * @param teacherId   id del Teacher
     * @param pageRequest
     */
    PaginatedResponse<StudentEvaluationModel> getEvaluationByTeacherIdAndCourseId(Integer courseId, Integer teacherId, PageRequest pageRequest);

    /**
     * Recupera tutte le valutazioni degli studenti di un determinato corso
     * @param courseId id del corso
     * @param pageRequest informazioni sulla paginazione
     */
    PaginatedResponse<StudentEvaluationModel> getByCourse(Integer courseId, PageRequest pageRequest);



    /**
     * Aggiorna la valutazione dello studente
     *
     * @param studentEvaluationModifyModel contiene i nuovi dati della valutazione
     * @param evaluationId                 id del Teacher associato al corso
     * @param courseId                     id della partecipazione del Corso
     * @return una Valutazione per lo studente aggiornata
     */
    StudentEvaluationModel update(StudentEvaluationModel studentEvaluationModifyModel, Integer evaluationId, Integer courseId);

    /**
     * Elimina una Valutazione
     * @param evaluationId id del Teacher associato a quel corso
     * @param courseId
     */
    void delete(Integer evaluationId, Integer courseId);

    /**
     * Verifica l'esistenza della Valutazione dello Studente
     * @param teacherId id del Teacher
     * @param courseId id del Corso
     * @param studentId id della partecipazione dello Studente al Corso
     */
    StudentEvaluationModel existEvaluation(Integer teacherId, Integer courseId, Integer studentId);

    /**
     * Recupera tutte le Valutazioni dello Studente
     * @param pageRequest contiene la Paginazione
     */
    PaginatedResponse<StudentEvaluationModel> getAll(PageRequest pageRequest);
}
