package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.course.Model.*;

public interface CourseService {

    /**
     * Aggiunge un Corso
     * @param courseModel contiene i dati del Corso
     * @return il Corso aggiunto
     */
    CourseModel add(CourseModel courseModel);

    /**
     * Recupera un singolo Corso
     * @param courseId id del Corso
     * @return singolo Corso
     */
    CourseModel get(Integer courseId);

    /**
     * Aggiorna i dati del Corso
     * @param courseModifyModel contiene i dati del corso
     * @param courseId id del corso
     * @return un Corso aggiornato
     */
    CourseModel update(CourseModel courseModifyModel,Integer courseId);

    /**
     * Elimina un Corso
     * @param courseId id del Corso
     */
    void delete(Integer courseId);

    /**
     * Candida un Teacher per un Corso
     * @param teacherCourseModel  Teacher associato al corso
     * @return la candidatura del Teacher associato al Corso
     */
    TeacherCourseModel addTeacher(TeacherCourseModel teacherCourseModel);

    /**
     * Aggiunge una Logistica associata per un Corso
     * @param logisticCourseModel contiene i dati della logistica
     * @return una Logistica associata per quel Corso
     */
    LogisticCourseModel addLogistic(LogisticCourseModel logisticCourseModel);

    /**
     * Accettazione/Rifiuto del Corso
     * @param courseId id del Corso
     * @param decision Accettazione o Rifiuto (ACCEPT/DENY)
     * @return un Corso accettato/rifiutato
     */
    CourseModel setStatusCourse(Integer courseId, DecisionEnum decision);

    /**
     * Accettazione/Rifiuto di una Logistica associata a quel Corso
     * @param courseId id del corso
     * @param logisticId id della Logistica
     * @param decision decisione di accettazione/rifiuto DENY/ACCEPT
     * @return una Logistica accettata/rifiutata
     */
    LogisticCourseModel setStatusLogisticCourse(Integer courseId, Integer logisticId , DecisionEnum decision);

    /**
     * Accettazione/Rifiuto del Tool associato per quel corso
     * @param courseId id del corso
     * @param toolId id dello strumento
     * @param decision decisione accettazione/rifiuto DENY/ACCEPT
     * @return un Tool Accettato/Rifiutato
     */
    ToolCourseModel setStatusToolCourse(Integer courseId, Integer toolId, DecisionEnum decision);

    /**
     * Accettazione/Rifiuto di un Teacher associato ad un Corso
     * @param courseId id del corso
     * @param teacherCourseId id del Teacher associato al corso
     * @param decision decisione di Accettazione/Rifiuto DENY / ACCEPT
     * @return un Teacher Accettato/Rifiutato
     */
    TeacherCourseModel setStatusTeacherCourse(Integer courseId, Integer teacherCourseId, DecisionEnum decision);

    /**
     * Aggiunge un'agenda associata a quel Corso
     * @param courseId id del corso
     * @param agendaModel contiene i dati del Corso
     * @return un' Agenda associata ad un Corso
     */
    AgendaModel addAgenda(Integer courseId, AgendaModel agendaModel);

    /**
     * Aggiunge un Tool per un determinato Corso
     * @param toolCourseModel dati del tool richiesta
     * @return un Tool associato per quel Corso
     */
    ToolCourseModel addTool(ToolCourseModel toolCourseModel);

}
