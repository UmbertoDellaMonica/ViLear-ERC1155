package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.LogisticCourseEntity;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Repository.LogisticCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import it.tcgroup.vilear.base.Payload.Exception.LogisticCourseNotFoundException;
import java.sql.Date;
import java.util.List;

@Service
public class LogisticCourseManagerImpl implements LogisticCourseManager{
    @Autowired
    private LogisticCourseRepository logisticCourseRepository;

    @Autowired
    private ModelMapper mapper;

    private void checkCourse(LogisticCourseEntity logisticCourseEntity, Integer courseId) {
        if(!logisticCourseEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public LogisticCourseEntity save(LogisticCourseModel logisticCourseModel) {
        Integer logisticId = logisticCourseModel.getLogisticId();
        Integer courseId = logisticCourseModel.getCourse().getId();
        LogisticCourseEntity.PrimaryKey primaryKey = new LogisticCourseEntity.PrimaryKey(
                logisticId,
                courseId
        );
        LogisticCourseEntity logisticCourseEntity=mapper.map(logisticCourseModel,LogisticCourseEntity.class);
        logisticCourseEntity.setPrimaryKey(primaryKey);

        return logisticCourseRepository.save(logisticCourseEntity);
    }

    @Override
    public boolean existsLogisticCourse(Integer logisticId, List<Date> dates) {
        if(logisticCourseRepository.existsLogisticCourse(logisticId, dates)==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public LogisticCourseEntity get(Integer logisticId,Integer courseId){
        LogisticCourseEntity logisticCourseEntity =  logisticCourseRepository
                .findById(new LogisticCourseEntity.PrimaryKey(logisticId,courseId))
                .orElse(null);
        if(logisticCourseEntity == null){
            return null;
        }else{
            checkCourse(logisticCourseEntity,courseId);
            return logisticCourseEntity;
        }
    }


    @Override
    public Page<LogisticCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest) {
        return logisticCourseRepository.findByCourse(courseId,pageRequest);
    }

    @Override
    public Page<LogisticCourseEntity> getAll(PageRequest pageRequest) {
        return logisticCourseRepository.findAll(pageRequest);
    }

    @Override
    public void deleteLogisticCourse(Integer logisticId,Integer courseId){
        LogisticCourseEntity logisticCourseEntity = get(logisticId, courseId);
        if(logisticCourseEntity == null){
            throw new LogisticCourseNotFoundException();
        }else{
            // Soft- Delete
            logisticCourseEntity.setStatus(StatusEnum.DELETED);
            logisticCourseRepository.save(logisticCourseEntity);
        }
    }

    @Override
    public List<LogisticCourseEntity> getByLogistic(Integer logisticId) {
        return logisticCourseRepository.findById(logisticId);
    }
}
