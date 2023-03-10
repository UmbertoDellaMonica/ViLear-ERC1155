package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.base.Payload.Exception.StatusNotFoundException;
import it.tcgroup.vilear.base.Payload.Exception.ToolCourseNotFoundException;
import it.tcgroup.vilear.course.Entity.LogisticCourseEntity;
import it.tcgroup.vilear.course.Entity.ToolCourseEntity;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import it.tcgroup.vilear.course.Repository.ToolCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
public class ToolCourseManagerImpl implements ToolCourseManager{

    @Autowired
    private ToolCourseRepository toolCourseRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ToolCourseEntity save(ToolCourseModel toolCourseModel) {
        Integer toolId = toolCourseModel.getToolId();
        Integer courseId = toolCourseModel.getCourse().getId();
        ToolCourseEntity.PrimaryKey primaryKey = new ToolCourseEntity.PrimaryKey(toolId, courseId);
        ToolCourseEntity toolCourseEntity=mapper.map(toolCourseModel,ToolCourseEntity.class);
        toolCourseEntity.setPrimaryKey(primaryKey);

        return toolCourseRepository.save(toolCourseEntity);
    }

    @Override
    public ToolCourseEntity get(Integer toolId,Integer courseId) {
        ToolCourseEntity.PrimaryKey primaryKey = new ToolCourseEntity.PrimaryKey(toolId,courseId);
        ToolCourseEntity toolCourseEntity = toolCourseRepository
                .findById(primaryKey)
                .orElse(null);
        if(toolCourseEntity == null){
            return null;
        }else{
            checkCourse(toolCourseEntity,courseId);
            return toolCourseEntity;
        }
    }

    private void checkCourse(ToolCourseEntity toolCourseEntity, Integer courseId) {
        if(!toolCourseEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public Page<ToolCourseEntity> getByCourse(Integer courseId, PageRequest pageRequest) {
        return toolCourseRepository.findByCourse(courseId,pageRequest);
    }

    @Override
    public Page<ToolCourseEntity> getAll(PageRequest pageRequest) {
        return toolCourseRepository.findAll(pageRequest);
    }

    @Override
    public void deleteToolCourse(Integer toolId,Integer courseId){
        ToolCourseEntity toolCourseEntity = get(toolId, courseId);
        if(toolCourseEntity == null){
            throw new ToolCourseNotFoundException();
        }else{
            toolCourseEntity.setStatus(StatusEnum.DELETED);
            toolCourseRepository.save(toolCourseEntity);
        }
    }

    @Override
    public List<Integer> getQuantity(Integer toolId, List<Date> dates) {
        return toolCourseRepository.getToolCourseDetails(toolId, dates);
    }

    @Override
    public List<ToolCourseEntity> getByTool(Integer toolId) {
        return toolCourseRepository.findByTool(toolId);

    }

}
