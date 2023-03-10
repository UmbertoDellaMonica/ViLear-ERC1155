package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;

public interface StudentService {

    /**
     * Candida uno studente per un corso,
     * Se lo studente ha almeno un ruolo come := STUDENTE
     *               => la candidatura è già accettata
     *  Altrimenti la candidatura è
     *      in stato sospeso
     *
     * @param courseId si riferisce all'id del corso
     * @param personId si riferisce all'id della persona
     * @return StudentPartecipationModel  */
    StudentPartecipationModel candidate(Integer courseId, Integer personId);

    /**
     * Accettazione/Rifiuto della Partecipazione di uno studente per quel corso
     *
     * @param studentPartecipationId id della partecipazione dello studente
     * @param courseId
     * @param decision               Accettazione/Rifiuto
     * @return una Partecipazione dello studente accettata o rifiutata
     */
    StudentPartecipationModel choose(Integer studentPartecipationId, Integer courseId, DecisionEnum decision);

    //--------------------------------------------------------------------------------------

    /**Aggiunge una valutazione per lo studente
     * @param studentEvaluationModel contiene i dati della valutazione dello studente
     * @return La valutazione inserita
     */
    StudentEvaluationModel addEvaluation(StudentEvaluationModel studentEvaluationModel);

    /**
     * Recupera una singola valutazione dello studente
     *
     * @param evaluationId id del Teacher che tiene quel corso
     * @param courseId     id del corso
     * @return una Singola Valutazione dello Studente
     */
    StudentEvaluationModel getEvaluationStudent(
            Integer evaluationId,
            Integer courseId
    );

    /**
     * Aggiorna i dati  della singola valutazione dello studente
     *
     * @param newStudentEvaluationModel contiene i nuovi dati della valutazione dello studente
     * @param evaluationId id dello studente che partecipa ad un corso
     * @param courseId id del Corso
     * @return aggiornamento della Valutazione dello studente
     */
    StudentEvaluationModel updateEvaluation(StudentEvaluationModel newStudentEvaluationModel,
                                            Integer evaluationId,
                                            Integer courseId);

    /**
     * Cancella la valutazione dello studente
     *
     * @param evaluationId id id della partecipazione dello studente
     * @param courseId
     */
    void deleteEvalutation(Integer evaluationId, Integer courseId);
}
