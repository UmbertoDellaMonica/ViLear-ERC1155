package it.tcgroup.vilear.course.Service.Manager;


import it.tcgroup.vilear.course.Entity.StudentEvaluationEntity;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EvaluationManager {

    /**
     * Salva la valutazione dello studente
     * @param studentEvaluationModel contiene i dati della Valutazione dello studente
     * @return i dati della Valutazione
     */
    StudentEvaluationEntity save(StudentEvaluationModel studentEvaluationModel);

    /**
     * Recupera la singola valutazione dello studente
     *
     * @param evaluationId id del docente associato al corso
     */
    StudentEvaluationEntity get(Integer evaluationId, Integer courseId);

    /**
     * Recupera la valutazione degli studenti con quel teacher e con quel corso
     * @param courseId id del corso
     * @param teacherId id del teacher
     * @param pageRequest contiene i dati della Paginazione
     */
    Page<StudentEvaluationEntity> getByTeacherIdAndCourseId(Integer courseId, Integer teacherId, PageRequest pageRequest);

    /**
     * Elimina
     *
     * @param evaluationId id del Teacher associato al Corso
     * @param courseId id del Corso
     */
    void delete(Integer evaluationId, Integer courseId);

    /**
     * Recupero della Paginazione della Valutazione degli studenti
     * @param pageRequest contiene la paginazione
     */
    Page<StudentEvaluationEntity> getAll( PageRequest pageRequest);

    /**
     * Verifica che la Valutazione di uno studente esista tramite :
     * @param teacherId id del Docente
     * @param courseId id del Corso
     * @param studentId id dello Studente
     */
    StudentEvaluationEntity existEvaluation(
            Integer teacherId,
            Integer courseId,
            Integer studentId);

    /**
     * Recupera tutte le Valutazioni degli studenti tramite il Corso
     * @param courseId id del Corso
     * @param pageRequest contiene la Paginazione
     */
    Page<StudentEvaluationEntity> getByCourse(Integer courseId, PageRequest pageRequest);
}
