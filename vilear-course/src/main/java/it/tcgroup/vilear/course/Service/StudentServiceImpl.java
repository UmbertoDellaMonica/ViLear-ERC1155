package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.*;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.course.Model.CourseModel;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    @Autowired
    private PartecipationService partecipationService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public StudentPartecipationModel candidate(Integer courseId, Integer personId) {
        // Verifica che il Corso esista
        CourseModel courseModel = courseService.get(courseId);

        // Verifica che la Persona esista
        PersonResponse personResponse = vilearRegistrationServiceClient.getPerson(personId);


        // Verifico che la Partecipazione dello studente non esista 
        StudentPartecipationModel studentPartecipationModel = partecipationService.get(courseId,personId);
        if(studentPartecipationModel!=null){
            throw new StudentPartecipationAlreadyExistsException();
        }else{
            studentPartecipationModel=new StudentPartecipationModel();
            studentPartecipationModel.setPersonId(personResponse.getId());
            studentPartecipationModel.setCourse(courseModel);
        }

        // Salva i dati della partecipazione dello studente
        return partecipationService.add(studentPartecipationModel);
    }

    /**
     * Verifica che lo stato della Partecipazione non sia già APPROVATO oppure NO
     * @param studentPartecipationModel contiene i dati della Partecipazione dello studente
     */
    private void checkStudentPartecipationStatus(StudentPartecipationModel studentPartecipationModel) {
        switch(studentPartecipationModel.getStatus()){
            case APPROVED -> throw new StatusAlreadyExistsException();
            case REJECTED -> throw new StatusAlreadyExistsException();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public StudentPartecipationModel choose(Integer studentPartecipationId, Integer courseId, DecisionEnum decision) {

        // Verifica che la Partecipazione dello studente esista
        StudentPartecipationModel studentPartecipationModel= partecipationService.get(courseId,studentPartecipationId);

        // Verifica che lo status non sia stato già approvato e aggiornato
        checkStudentPartecipationStatus(studentPartecipationModel);

        // Verifica qual'è la decisione presa e in tal caso modifico la Partecipazione
        switch(decision){
            case ACCEPT : {
                studentPartecipationModel.setStatus(StatusEnum.APPROVED);
                break;
            }
            case DENY:{
                studentPartecipationModel.setStatus(StatusEnum.REJECTED);
                break;
            }
        }

        // Aggiorna i dati della Partecipazione dello studente
        return partecipationService.update(studentPartecipationModel, studentPartecipationId,courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public StudentEvaluationModel addEvaluation(StudentEvaluationModel studentEvaluationModel ) {

        Integer teacherId = studentEvaluationModel.getTeacherCourse().getTeacherId();
        Integer courseId = studentEvaluationModel.getCourse().getId();
        Integer studentPartecipationId = studentEvaluationModel.getStudentPartecipation().getPersonId();

        // Recupero il Teacher del Corso
        TeacherCourseModel teacherCourseModel = teacherCourseService.get(teacherId,courseId);

        // Recupera il Corso
        CourseModel courseModel = courseService.get(courseId);

        // Verifica che la partecipazione dello Studente  esista oppure no
        StudentPartecipationModel studentPartecipationModel = partecipationService.get(courseId, studentPartecipationId);
        if(studentPartecipationModel == null){
            throw new StudentPartecipationNotFoundException();
        }

        // Verifica che lo status della partecipazione sia Accettata altrimenti manda un'eccezione
        // Verifica che lo status del Docente associatio al Corso sia Accettata altrimenti manda un'eccezione
        if(
                StatusEnum.REJECTED.equals( studentPartecipationModel.getStatus() ) ||
                StatusEnum.PENDING.equals( studentPartecipationModel.getStatus() ) ||
                StatusEnum.REJECTED.equals( teacherCourseModel.getStatus() ) ||
                StatusEnum.PENDING.equals( teacherCourseModel.getStatus() )
        ){
            throw new StatusNotFoundException();
        }
        // Verifico che non esista già la vlautazione di uno Studente
        StudentEvaluationModel studentEvaluation = evaluationService.existEvaluation(
                teacherCourseModel.getTeacherId(),
                courseModel.getId(),
                studentPartecipationModel.getPersonId()
        );
        if(studentEvaluation!=null){
            throw new StudentEvaluationAlreadyExistsException();
        } else {
            studentEvaluationModel.setStudentPartecipation(studentPartecipationModel);
            studentEvaluationModel.setCourse(courseModel);
            studentEvaluationModel.setTeacherCourse(teacherCourseModel);
        }

        //Salva i dati della Valutazione dello Studente
        return evaluationService.add(studentEvaluationModel);
    }

    @Override
    @Transactional
    public StudentEvaluationModel getEvaluationStudent(Integer evaluationId, Integer courseId) {

        return evaluationService.get(evaluationId,courseId);
    }

    @Override
    public StudentEvaluationModel updateEvaluation(StudentEvaluationModel newStudentEvaluationModel,
                                                   Integer evaluationId,
                                                   Integer courseId) {
        // Aggiorna i dati
        return evaluationService.update(newStudentEvaluationModel, evaluationId, courseId);
    }

    @Override
    @Transactional
    public void deleteEvalutation(Integer evaluationId, Integer courseId) {
        evaluationService.delete(evaluationId,courseId);
    }

}
