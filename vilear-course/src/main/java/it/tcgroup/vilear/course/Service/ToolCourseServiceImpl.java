package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.ToolCourseEntity;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import it.tcgroup.vilear.course.Service.Manager.ToolCourseManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToolCourseServiceImpl implements ToolCourseService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ToolCourseManager toolCourseManager;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolCourseModel add(ToolCourseModel toolCourseModel) {
        // Salva i dati del Tool associato al corso
        ToolCourseEntity toolCourseEntity = toolCourseManager.save(toolCourseModel);

        return mapper.map(toolCourseEntity,ToolCourseModel.class);
    }

    @Override
    @Transactional
    public ToolCourseModel get(Integer courseId, Integer toolId) {
        ToolCourseEntity toolCourseEntity = toolCourseManager.get(toolId,courseId);
        if (toolCourseEntity == null) {
                return null;
        }else{
            ToolCourseModel toolCourseModel = mapper.map(toolCourseEntity, ToolCourseModel.class);
            return toolCourseModel;
        }
    }

    @Override
    public PaginatedResponse<ToolCourseModel> getByCourse(Integer courseId, PageRequest pageRequest) {
        Page<ToolCourseEntity>toolCourseEntityPage = toolCourseManager.getByCourse(courseId,pageRequest);
        List<ToolCourseModel>toolCourseModelList = mapper.map(toolCourseEntityPage.getContent(),new TypeToken<List<ToolCourseModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(toolCourseEntityPage.getTotalPages());
        pageData.setTotalElements(toolCourseEntityPage.getTotalElements());

        PaginatedResponse<ToolCourseModel>toolCourseModelPaginatedResponse = new PaginatedResponse<>();
        toolCourseModelPaginatedResponse.setPageData(pageData);
        toolCourseModelPaginatedResponse.setData(toolCourseModelList);

        return toolCourseModelPaginatedResponse;
    }

    @Override
    public List<ToolCourseModel> updateByTool(Integer toolId) {
        List<ToolCourseEntity>toolCourseEntities = toolCourseManager.getByTool(toolId);
        List<ToolCourseModel>teacherCourseModelList = new ArrayList<>();
        if(toolCourseEntities.isEmpty()){
            return null;
        }
        for(ToolCourseEntity toolCourseEntity:toolCourseEntities){
            // Setto lo stato di eliminazione
            toolCourseEntity.setStatus(StatusEnum.DELETED);
            // Mappo il Teacher
            ToolCourseModel toolCourseModel = mapper.map(toolCourseEntity, ToolCourseModel.class);
            // Modifico il TeacherCourse
            toolCourseEntity = toolCourseManager.save(toolCourseModel);
            // Lo aggiungo nella Lista da restituire
            teacherCourseModelList.add(mapper.map(toolCourseEntity, ToolCourseModel.class));
        }
        return teacherCourseModelList;
    }

    @Override
    public PaginatedResponse<ToolCourseModel> getAll(PageRequest pageRequest) {
        Page<ToolCourseEntity>toolCourseEntityPage = toolCourseManager.getAll(pageRequest);
        List<ToolCourseModel>toolCourseModelList = mapper.map(toolCourseEntityPage.getContent(),new TypeToken<List<ToolCourseModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(toolCourseEntityPage.getTotalPages());
        pageData.setTotalElements(toolCourseEntityPage.getTotalElements());

        PaginatedResponse<ToolCourseModel>toolCourseModelPaginatedResponse = new PaginatedResponse<>();
        toolCourseModelPaginatedResponse.setPageData(pageData);
        toolCourseModelPaginatedResponse.setData(toolCourseModelList);

        return toolCourseModelPaginatedResponse;
    }

    @Override
    @Transactional
    public ToolCourseModel update(ToolCourseModel toolCourseModifyModel, Integer toolId, Integer courseId) {

        // Aggiorno il dato del Tool associato al corso
        ToolCourseModel toolCourseModel=get(courseId, toolId);
        toolCourseModel.setStatus(toolCourseModifyModel.getStatus());
        toolCourseModel.setToolId(toolId);

        ToolCourseEntity toolCourseEntity = toolCourseManager.save(toolCourseModel);

        return  mapper.map(toolCourseEntity,ToolCourseModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer courseId, Integer toolId) {
        toolCourseManager.deleteToolCourse(toolId,courseId);
    }

    @Override
    @Transactional
    public List<Integer> getSummaryQuantity(Integer toolId, List<Date> dates) {
        return toolCourseManager.getQuantity(toolId,dates);
    }
}
