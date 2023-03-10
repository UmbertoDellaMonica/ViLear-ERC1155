package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.base.Payload.Exception.StudentEvaluationNotFoundException;
import it.tcgroup.vilear.course.Entity.StudentEvaluationEntity;
import it.tcgroup.vilear.course.Entity.StudentPartecipationEntity;
import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Repository.StudentEvaluationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EvaluationManagerImpl implements EvaluationManager {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;

    @Override
    public StudentEvaluationEntity save(StudentEvaluationModel studentEvaluationModel) {

        StudentEvaluationEntity studentEvaluationEntity = mapper.map(studentEvaluationModel,StudentEvaluationEntity.class);

        StudentPartecipationEntity.PrimaryKey primaryKeyStudent=new StudentPartecipationEntity.PrimaryKey();
        primaryKeyStudent.setCourseId(studentEvaluationEntity.getCourse().getId());
        primaryKeyStudent.setPersonId(studentEvaluationModel.getStudentPartecipation().getPersonId());

        TeacherCourseEntity.PrimaryKey primaryKeyTeacher = new TeacherCourseEntity.PrimaryKey();
        primaryKeyTeacher.setCourseId(studentEvaluationEntity.getCourse().getId());
        primaryKeyTeacher.setTeacherId(studentEvaluationModel.getTeacherCourse().getTeacherId());

        studentEvaluationEntity.getTeacherCourse().setPrimaryKey(primaryKeyTeacher);
        studentEvaluationEntity.getStudentPartecipation().setPrimaryKey(primaryKeyStudent);

        return studentEvaluationRepository.save(studentEvaluationEntity);
    }

    @Override
    public StudentEvaluationEntity get(Integer evaluationId, Integer courseId){
        // Recupera la valutazione
        StudentEvaluationEntity studentEvaluationEntity = studentEvaluationRepository
                .findById(evaluationId)
                .orElseThrow(()->new StudentEvaluationNotFoundException());
        // Verifica che il corso associato sia quello giusto
        checkCourse(studentEvaluationEntity,courseId);

        return studentEvaluationEntity;
    }

    private void checkCourse(StudentEvaluationEntity studentEvaluationEntity, Integer courseId) {
        if(!studentEvaluationEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public Page<StudentEvaluationEntity> getByTeacherIdAndCourseId(Integer courseId, Integer teacherId,
                                                                   PageRequest pageRequest) {
        return studentEvaluationRepository.findByTeacherIdAndCourseId(courseId,teacherId,pageRequest);
    }

    @Override
    public void delete(Integer evaluationId, Integer courseId){
        StudentEvaluationEntity studentEvaluationEntity = get(evaluationId, courseId);

        studentEvaluationRepository.delete(studentEvaluationEntity);
    }

    @Override
    public Page<StudentEvaluationEntity> getByCourse(Integer courseId, PageRequest pageRequest) {
        return studentEvaluationRepository.findByCourse(courseId,pageRequest);
    }

    @Override
    public StudentEvaluationEntity existEvaluation(
            Integer teacherId,
            Integer courseId,
            Integer studentId
    ) {
        StudentEvaluationEntity studentEvaluationEntity = studentEvaluationRepository
                .findEvaluation(courseId,teacherId,studentId)
                .orElse(null);
        if(studentEvaluationEntity == null){
            return null;
        }else{
            checkCourse(studentEvaluationEntity,courseId);
            return studentEvaluationEntity;
        }
    }

    @Override
    public Page<StudentEvaluationEntity> getAll(PageRequest pageRequest) {
        return studentEvaluationRepository.findAll(pageRequest);
    }

}
