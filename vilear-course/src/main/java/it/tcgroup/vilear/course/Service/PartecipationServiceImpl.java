package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.base.Payload.Response.StudentPartecipationResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.course.Entity.StudentEvaluationEntity;
import it.tcgroup.vilear.course.Entity.StudentPartecipationEntity;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import it.tcgroup.vilear.course.Service.Manager.PartecipationManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartecipationServiceImpl implements PartecipationService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartecipationManager partecipationManager;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    @Override
    @Transactional
    public StudentPartecipationModel add(StudentPartecipationModel studentPartecipationModel) {
        // Effettua la chiamata per recuperare il Role Person
        RolePersonResponse rolePersonResponse = vilearRegistrationServiceClient.getRolePerson(studentPartecipationModel);
        // Verifica quella persona ha un ruolo di uno studente
        if(rolePersonResponse!=null){
            studentPartecipationModel.setStatus(StatusEnum.APPROVED);
        }else{
            studentPartecipationModel.setStatus(StatusEnum.PENDING);
        }
        // Salva i dati della partecipazione dello studente
        StudentPartecipationEntity studentPartecipationEntity = partecipationManager.save(studentPartecipationModel);

        return mapper.map(studentPartecipationEntity,StudentPartecipationModel.class);
    }

    @Override
    @Transactional
    public StudentPartecipationModel get(Integer courseId, Integer studentPartecipationId) {
        StudentPartecipationEntity studentPartecipationEntity = partecipationManager.get(courseId, studentPartecipationId);
        if (studentPartecipationEntity == null) {
            return null;
        }else{
             return mapper.map(studentPartecipationEntity, StudentPartecipationModel.class);
        }
    }

    @Override
    @Transactional
    public StudentPartecipationModel update(StudentPartecipationModel studentPartecipationModifyModel, Integer studentId,Integer courseId){
        // Verifica se esiste la partecipazione dello Studente altrimenti lancia un'eccezione
        StudentPartecipationModel studentPartecipationModel=get(courseId,studentId);
        studentPartecipationModel.setStatus(studentPartecipationModifyModel.getStatus());

        StudentPartecipationEntity studentPartecipationEntity = partecipationManager.save(studentPartecipationModel);

        return mapper.map(studentPartecipationEntity,StudentPartecipationModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer studentId, Integer courseId) {
       partecipationManager.delete(studentId, courseId);
    }

    @Override
    public PaginatedResponse<StudentPartecipationModel> getByCourse(Integer courseId, PageRequest pageRequest) {
        Page<StudentPartecipationEntity> partecipationEntityPage = partecipationManager.getByCourse(courseId,pageRequest);

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(partecipationEntityPage.getTotalPages());
        pageData.setTotalElements(partecipationEntityPage.getTotalElements());

        List<StudentPartecipationModel>studentPartecipationModelList = mapper.map(partecipationEntityPage.getContent(),new TypeToken<List<StudentPartecipationModel>>(){}.getType());
        PaginatedResponse<StudentPartecipationModel>studentPartecipationModelPaginatedResponse = new PaginatedResponse<>(studentPartecipationModelList,pageData);

        return studentPartecipationModelPaginatedResponse;
    }

    @Override
    public PaginatedResponse<StudentPartecipationModel> getAll(PageRequest pageRequest) {
        Page<StudentPartecipationEntity> partecipationEntityPage = partecipationManager.getAll(pageRequest);

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(partecipationEntityPage.getTotalPages());
        pageData.setTotalElements(partecipationEntityPage.getTotalElements());

        List<StudentPartecipationModel>studentPartecipationModelList = mapper.map(partecipationEntityPage.getContent(),new TypeToken<List<StudentPartecipationModel>>(){}.getType());
        PaginatedResponse<StudentPartecipationModel>studentPartecipationModelPaginatedResponse = new PaginatedResponse<>(studentPartecipationModelList,pageData);

        return studentPartecipationModelPaginatedResponse;
    }

}
