package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.TeacherCourseEntity;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Service.Manager.TeacherCourseManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherCourseManager teacherCourseManager;

    @Override
    @Transactional
    public TeacherCourseModel add(TeacherCourseModel teacherCourseModel) {
        TeacherCourseEntity teacherCourseEntity = teacherCourseManager.save(teacherCourseModel);

        return mapper.map(teacherCourseEntity, TeacherCourseModel.class);
    }

    @Override
    public PaginatedResponse<TeacherCourseModel> getByCourse(Integer courseId, PageRequest pageRequest) {
        Page<TeacherCourseEntity>teacherCourseEntityPage = teacherCourseManager.getByCourse(courseId,pageRequest);
        List<TeacherCourseModel>teacherCourseModelList = mapper.map(
                teacherCourseEntityPage.getContent(),
                new TypeToken<List<TeacherCourseModel>>(){}.getType()
        );
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(teacherCourseEntityPage.getTotalPages());
        pageData.setTotalElements(teacherCourseEntityPage.getTotalElements());

        PaginatedResponse<TeacherCourseModel>paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setData(teacherCourseModelList);
        paginatedResponse.setPageData(pageData);

        return paginatedResponse;
    }

    @Override
    public PaginatedResponse<TeacherCourseModel> getAll(PageRequest pageRequest) {
        Page<TeacherCourseEntity>teacherCourseEntityPage = teacherCourseManager.getAll(pageRequest);
        List<TeacherCourseModel>teacherCourseModelList = mapper.map(
                teacherCourseEntityPage.getContent(),
                new TypeToken<List<TeacherCourseModel>>(){}.getType()
        );
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(teacherCourseEntityPage.getTotalPages());
        pageData.setTotalElements(teacherCourseEntityPage.getTotalElements());

        PaginatedResponse<TeacherCourseModel>paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setData(teacherCourseModelList);
        paginatedResponse.setPageData(pageData);

        return paginatedResponse;
    }

    @Override
    public List<TeacherCourseModel> updateByTeacher(Integer teacherId) {
        List<TeacherCourseEntity>teacherCourseEntities = teacherCourseManager.getByTeacher(teacherId);
        List<TeacherCourseModel>teacherCourseModelList = new ArrayList<>();
        if(teacherCourseEntities.isEmpty()){
            return null;
        }
        for(TeacherCourseEntity teacherCourseEntity:teacherCourseEntities){
            // Setto lo stato di eliminazione
            teacherCourseEntity.setStatus(StatusEnum.DELETED);
            // Mappo il Teacher
            TeacherCourseModel teacherCourseModel = mapper.map(teacherCourseEntity, TeacherCourseModel.class);
            // Modifico il TeacherCourse
            teacherCourseEntity = teacherCourseManager.save(teacherCourseModel);
            // Lo aggiungo nella Lista da restituire
            teacherCourseModelList.add(mapper.map(teacherCourseEntity, TeacherCourseModel.class));
        }
        return teacherCourseModelList;
    }

    @Override
    @Transactional
    public TeacherCourseModel get(Integer teacherId,Integer courseId) {
        TeacherCourseEntity teacherCourseEntityOpt = teacherCourseManager.get(teacherId,courseId);

        return mapper.map(teacherCourseEntityOpt,TeacherCourseModel.class);
    }


    @Override
    @Transactional
    public TeacherCourseModel update(TeacherCourseModel teacherCourseModifyModel, Integer teacherId,Integer courseId) {
        // Verifica se il Teacher associato al corso esiste
        TeacherCourseModel teacherCourseModel = get(teacherId, courseId);
        // Imposta i dati
        teacherCourseModel.setStatus(teacherCourseModifyModel.getStatus());
        teacherCourseModel.setTeacherId(teacherId);
        // Modifica i dati del Teacher associato al corso
        TeacherCourseEntity teacherCourseEntity = teacherCourseManager.save(teacherCourseModel);

        teacherCourseModel = mapper.map(teacherCourseEntity,TeacherCourseModel.class);
        teacherCourseModel.setTeacherId(teacherId);
        return teacherCourseModel;
    }

    @Override
    @Transactional
    public void delete(Integer teacherCourseId, Integer courseId) {
        teacherCourseManager.deleteTeacherCourse(teacherCourseId,courseId);
    }

    @Override
    @Transactional
    public boolean exists(Integer teacherPersonId, List<Date> agendaDays) {
        return teacherCourseManager.existsTeacherCourse(teacherPersonId,agendaDays);
    }


}
