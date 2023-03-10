package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.base.Payload.Exception.TeacherCourseNotFoundException;
import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Repository.TeacherCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherCourseManagerImpl implements TeacherCourseManager{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    private void checkCourse(TeacherCourseEntity teacherCourseEntity, Integer courseId) {
        if(!teacherCourseEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public TeacherCourseEntity save(TeacherCourseModel teacherCourseModel) {

        TeacherCourseEntity.PrimaryKey primaryKey = new TeacherCourseEntity.PrimaryKey(teacherCourseModel.getTeacherId(), teacherCourseModel.getCourse().getId());

        TeacherCourseEntity teacherCourseEntity = mapper.map(teacherCourseModel,TeacherCourseEntity.class);
        teacherCourseEntity.setPrimaryKey(primaryKey);

        return teacherCourseRepository.save(teacherCourseEntity);
    }

    @Override
    public TeacherCourseEntity get(Integer teacherId,Integer courseId) {
       TeacherCourseEntity.PrimaryKey primaryKey = new TeacherCourseEntity.PrimaryKey(teacherId,courseId);

       TeacherCourseEntity teacherCourseEntity = teacherCourseRepository.findById(primaryKey)
                .orElseThrow(()->new TeacherCourseNotFoundException());
       checkCourse(teacherCourseEntity,courseId);

       return teacherCourseEntity;
    }


    @Override
    public Page<TeacherCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest) {
        return teacherCourseRepository.findByCourse(courseId,pageRequest);
    }

    @Override
    public Page<TeacherCourseEntity> getAll(PageRequest pageRequest) {
        return teacherCourseRepository.findAll(pageRequest);
    }

    @Override
    public void deleteTeacherCourse(Integer teacherId, Integer courseId){
        TeacherCourseEntity teacherCourseEntity = get(teacherId, courseId);
        teacherCourseEntity.setStatus(StatusEnum.DELETED);

        teacherCourseRepository.save(teacherCourseEntity);
    }

    @Override
    public boolean existsTeacherCourse(Integer teacherId, List<Date> days) {
        if(teacherCourseRepository.existsTeacherCourse(teacherId,days)==null){
            return false;
        }
        return true;
    }

    @Override
    public List<TeacherCourseEntity> getByTeacher(Integer teacherId) {
        return teacherCourseRepository.findById(teacherId);
    }
}
