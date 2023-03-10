package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.StudentEvaluationEntity;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Service.Manager.EvaluationManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EvaluationManager evaluationManager;

    @Override
    @Transactional
    public StudentEvaluationModel add(StudentEvaluationModel studentEvaluationModel) {
        // Salva la Valutazione dello studente
        StudentEvaluationEntity studentEvaluationEntity = evaluationManager.save(studentEvaluationModel);

        return mapper.map(studentEvaluationEntity,StudentEvaluationModel.class);
    }


    @Override
    @Transactional
    public StudentEvaluationModel get(Integer evaluationId, Integer courseId) {
        StudentEvaluationEntity studentEvaluationEntity = evaluationManager.get(evaluationId, courseId);

        return mapper.map(studentEvaluationEntity,StudentEvaluationModel.class);
    }

    @Override
    public PaginatedResponse<StudentEvaluationModel> getEvaluationByTeacherIdAndCourseId(Integer courseId, Integer teacherId, PageRequest pageRequest) {
        Page<StudentEvaluationEntity> studentEvaluationEntityPage = evaluationManager.getByTeacherIdAndCourseId(courseId,teacherId,pageRequest);

        List<StudentEvaluationModel>studentEvaluationModelList = mapper.map(
                studentEvaluationEntityPage.getContent(),
                new TypeToken<List<StudentEvaluationModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(studentEvaluationEntityPage.getTotalElements());
        pageData.setTotalPages(studentEvaluationEntityPage.getTotalPages());

        PaginatedResponse<StudentEvaluationModel>paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setPageData(pageData);
        paginatedResponse.setData(studentEvaluationModelList);

        return paginatedResponse;
    }

    @Override
    public PaginatedResponse<StudentEvaluationModel> getByCourse(Integer courseId, PageRequest pageRequest) {
        Page<StudentEvaluationEntity>studentEvaluationEntityPage = evaluationManager.getByCourse(courseId,pageRequest);
        List<StudentEvaluationModel>studentEvaluationModelList = mapper.map(studentEvaluationEntityPage.getContent(),new TypeToken<List<StudentEvaluationModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(studentEvaluationEntityPage.getTotalElements());
        pageData.setTotalPages(studentEvaluationEntityPage.getTotalPages());

        PaginatedResponse<StudentEvaluationModel>paginatedResponse = new PaginatedResponse<>(studentEvaluationModelList,pageData);
        return paginatedResponse;
    }

    @Override
    public StudentEvaluationModel update(
            StudentEvaluationModel studentEvaluationModifyModel, Integer evaluationId, Integer courseId){
        // Recupero i dati della valutazione
        StudentEvaluationModel studentEvaluationModel = get(evaluationId,courseId);
        // Imposto i dati della valutazione dello Studente
        studentEvaluationModifyModel.setStudentPartecipation(studentEvaluationModel.getStudentPartecipation());
        studentEvaluationModifyModel.setCourse(studentEvaluationModel.getCourse());
        studentEvaluationModifyModel.setTeacherCourse(studentEvaluationModel.getTeacherCourse());
        studentEvaluationModifyModel.setCreatedAt(studentEvaluationModel.getCreatedAt());
        studentEvaluationModifyModel.setId(studentEvaluationModel.getId());
        // Salva la Valutazione dello Studente
        StudentEvaluationEntity studentEvaluationEntity = evaluationManager.save(studentEvaluationModifyModel);

        return mapper.map(studentEvaluationEntity,StudentEvaluationModel.class);
    }

    @Override
    public void delete(Integer evaluationId, Integer courseId) {
        evaluationManager.delete(evaluationId,courseId);
    }

    @Override
    public StudentEvaluationModel existEvaluation(Integer teacherId, Integer courseId, Integer studentId) {

        StudentEvaluationEntity studentEvaluationEntity = evaluationManager.existEvaluation(teacherId, courseId, studentId);
        if (studentEvaluationEntity == null) {
            return null;
        } else {
            return mapper.map(studentEvaluationEntity, StudentEvaluationModel.class);
        }
    }

    @Override
    public PaginatedResponse<StudentEvaluationModel> getAll(PageRequest pageRequest) {
        Page<StudentEvaluationEntity>studentEvaluationEntityPage = evaluationManager.getAll(pageRequest);
        List<StudentEvaluationModel>studentEvaluationModelList = mapper.map(studentEvaluationEntityPage.getContent(),new TypeToken<List<StudentEvaluationModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(studentEvaluationEntityPage.getTotalElements());
        pageData.setTotalPages(studentEvaluationEntityPage.getTotalPages());

        PaginatedResponse<StudentEvaluationModel>paginatedResponse = new PaginatedResponse<>(studentEvaluationModelList,pageData);
        return paginatedResponse;
    }

}
