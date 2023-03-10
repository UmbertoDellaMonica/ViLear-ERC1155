package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.base.Payload.Exception.StudentPartecipationNotFoundException;
import it.tcgroup.vilear.course.Entity.StudentPartecipationEntity;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import it.tcgroup.vilear.course.Repository.StudentPartecipationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PartecipationManagerImpl implements PartecipationManager {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentPartecipationRepository studentPartecipationRepository;

    private void checkCourse(StudentPartecipationEntity studentPartecipationEntity, Integer courseId) {
        if(!studentPartecipationEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public StudentPartecipationEntity save(StudentPartecipationModel studentPartecipationModel) {
        Integer studentId = studentPartecipationModel.getPersonId();
        Integer courseId = studentPartecipationModel.getCourse().getId();

        StudentPartecipationEntity.PrimaryKey primaryKey = new StudentPartecipationEntity.PrimaryKey(studentId,courseId);
        StudentPartecipationEntity studentPartecipationEntity = mapper.map(studentPartecipationModel,StudentPartecipationEntity.class);
        studentPartecipationEntity.setPrimaryKey(primaryKey);

        return studentPartecipationRepository.save(studentPartecipationEntity);
    }

    @Override
    public StudentPartecipationEntity get(Integer courseId, Integer studentId){
        StudentPartecipationEntity.PrimaryKey primaryKey = new StudentPartecipationEntity.PrimaryKey(studentId,courseId);
        StudentPartecipationEntity studentPartecipationEntity = studentPartecipationRepository
                .findById(primaryKey)
                .orElse(null);
        if(studentPartecipationEntity == null){
            return null;
        }else{
            checkCourse(studentPartecipationEntity,courseId);
            return studentPartecipationEntity;
        }
    }

    @Override
    public void delete(Integer studentId, Integer courseId) {
        StudentPartecipationEntity studentPartecipationEntity = get(courseId,studentId);
        if(studentPartecipationEntity == null){
            throw new StudentPartecipationNotFoundException();
        }else{
            studentPartecipationRepository.delete(studentPartecipationEntity);
        }
    }

    @Override
    public Page<StudentPartecipationEntity> getAll(PageRequest pageRequest) {
        return studentPartecipationRepository.findAll(pageRequest);
    }

    @Override
    public Page<StudentPartecipationEntity> getByCourse(Integer courseId, PageRequest pageRequest) {
        return studentPartecipationRepository.findByCourse(courseId,pageRequest);
    }

}
