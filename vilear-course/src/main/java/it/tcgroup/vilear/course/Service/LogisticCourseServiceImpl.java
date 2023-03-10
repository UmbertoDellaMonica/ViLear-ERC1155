package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.course.Entity.LogisticCourseEntity;
import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Service.Manager.LogisticCourseManager;
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
public class LogisticCourseServiceImpl implements LogisticCourseService{

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private LogisticCourseManager logisticCourseManager;


    @Override
    @Transactional
    public LogisticCourseModel add(LogisticCourseModel logisticCourseModel) {
        // Aggiungo i dati
        LogisticCourseEntity logisticCourseEntity = logisticCourseManager.save(logisticCourseModel);
        LogisticCourseModel newLogisticCourseModel = mapper.map(logisticCourseEntity,LogisticCourseModel.class);

        return newLogisticCourseModel;
    }


    @Override
    @Transactional
    public LogisticCourseModel get(Integer courseId, Integer logisticId) {
        LogisticCourseEntity logisticCourseEntity = logisticCourseManager.get(logisticId,courseId);
        if (logisticCourseEntity == null) {
            return null;
        } else {
            LogisticCourseModel logisticCourseModel = mapper.map(logisticCourseEntity, LogisticCourseModel.class);
            return logisticCourseModel;
        }
    }

    @Override
    public PaginatedResponse<LogisticCourseModel> getByCourse(Integer courseId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        Page<LogisticCourseEntity>logisticCourseEntityPage = logisticCourseManager.getByCourse(courseId,pageRequest);
        List<LogisticCourseModel>logisticCourseModelList = mapper.map(
                logisticCourseEntityPage.getContent(),
                new TypeToken<List<LogisticCourseModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(logisticCourseEntityPage.getTotalPages());
        pageData.setTotalElements(logisticCourseEntityPage.getTotalElements());

        PaginatedResponse<LogisticCourseModel>logisticCourseModelPaginatedResponse = new PaginatedResponse<>();
        logisticCourseModelPaginatedResponse.setData(logisticCourseModelList);
        logisticCourseModelPaginatedResponse.setPageData(pageData);
        return logisticCourseModelPaginatedResponse;
    }

    @Override
    public List<LogisticCourseModel> updateByLogistic(Integer logisticId) {
        List<LogisticCourseEntity>logisticCourseEntities = logisticCourseManager.getByLogistic(logisticId);
        List<LogisticCourseModel>logisticCourseModelList = new ArrayList<>();
        if(logisticCourseEntities.isEmpty()){
            return null;
        }
        for(LogisticCourseEntity logisticCourseEntity:logisticCourseEntities){
            // Setto lo stato di eliminazione
            logisticCourseEntity.setStatus(StatusEnum.DELETED);
            // Mappo il Teacher
            LogisticCourseModel logisticCourseModel = mapper.map(logisticCourseEntity, LogisticCourseModel.class);
            // Modifico il TeacherCourse
            logisticCourseEntity = logisticCourseManager.save(logisticCourseModel);
            // Lo aggiungo nella Lista da restituire
            logisticCourseModelList.add(mapper.map(logisticCourseEntity, LogisticCourseModel.class));
        }
        return logisticCourseModelList;
    }

    @Override
    public PaginatedResponse<LogisticCourseModel> getAll(PageRequest pageRequest) {
        Page<LogisticCourseEntity>logisticCourseEntityPage = logisticCourseManager.getAll(pageRequest);
        List<LogisticCourseModel>logisticCourseModelList = mapper.map(
                logisticCourseEntityPage.getContent(),
                new TypeToken<List<LogisticCourseModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(logisticCourseEntityPage.getTotalPages());
        pageData.setTotalElements(logisticCourseEntityPage.getTotalElements());

        PaginatedResponse<LogisticCourseModel>logisticCourseModelPaginatedResponse = new PaginatedResponse<>();
        logisticCourseModelPaginatedResponse.setData(logisticCourseModelList);
        logisticCourseModelPaginatedResponse.setPageData(pageData);
        return logisticCourseModelPaginatedResponse;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LogisticCourseModel update(LogisticCourseModel logisticCourseModifyModel, Integer logisticId, Integer courseId) {

        LogisticCourseModel logisticCourseModel= get(courseId, logisticId);
        logisticCourseModel.setStatus(logisticCourseModifyModel.getStatus());
        logisticCourseModel.setLogisticId(logisticId);

        LogisticCourseEntity logisticCourseEntity = logisticCourseManager.save(logisticCourseModel);
        return mapper.map(logisticCourseEntity, LogisticCourseModel.class);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteLogisticCourse(Integer courseId, Integer logisticId) {
        logisticCourseManager.deleteLogisticCourse(logisticId,courseId);
    }

    @Override
    @Transactional
    public boolean existsLogisticCourse(Integer logisticId, List<Date> dates) {
        return logisticCourseManager.existsLogisticCourse(logisticId, dates);
    }
}
