package it.tcgroup.vilear.partner.Service;

import it.tcgroup.vilear.base.Payload.Exception.PartnerIsNotAdmin;
import it.tcgroup.vilear.partner.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.partner.Entity.PartnerEntity;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.partner.Service.Manager.PartnerManager;

import it.tcgroup.vilear.base.Payload.Exception.PartnerAdminAlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Exception.TeacherAlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private PartnerManager partnerManager;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PartnerModel add(PartnerModel partnerModel) {
        // Salvo i dati del Partner
        PartnerEntity partnerEntity = partnerManager.save(partnerModel);

        return mapper.map(partnerEntity,PartnerModel.class);
    }

    @Override
    @Transactional
    public PartnerModel get(Integer partnerId){
        PartnerEntity partnerEntity = partnerManager.get(partnerId);

        return mapper.map(partnerEntity,PartnerModel.class);
    }

    @Override
    public PartnerModel getByUser(Integer userId) {
        PartnerEntity partnerEntity = partnerManager.getByUser(userId);
        if(partnerEntity == null){
            return null;
        }

            return mapper.map(partnerEntity, PartnerModel.class);
    }

    @Override
    public PaginatedResponse<PartnerModel> getPartners(Integer page, Integer pageSize) {
        Page<PartnerEntity>partnerEntityPage = partnerManager.getAll(page,pageSize);
        List<PartnerModel>partnerModelList = mapper.map(partnerEntityPage.getContent(),new TypeToken<List<PartnerModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(partnerEntityPage.getTotalPages());
        pageData.setTotalElements(partnerEntityPage.getTotalElements());
        PaginatedResponse<PartnerModel>partnerModelPaginatedResponse = new PaginatedResponse<>();
        partnerModelPaginatedResponse.setPageData(pageData);
        partnerModelPaginatedResponse.setData(partnerModelList);
        return partnerModelPaginatedResponse;
    }

    @Override
    @Transactional
    public PartnerModel setAdministrator(Integer partnerId, Boolean admin) {
        // Recupero il Partner e verifica che esista
        PartnerModel partnerModel = get(partnerId);
        /*
            Se il Partner è già Admin => lancia un'eccezione
                Altrimenti  => setta lo stato dell'admin
         */
        if(partnerModel.getAdmin()){
            throw new PartnerAdminAlreadyExistsException();
        }else {
            partnerModel.setAdmin(admin);
        }
        // Aggiorna i dati del Partner
        return update(partnerModel, partnerId);
    }

    @Override
    @Transactional
    public PartnerModel update(PartnerModel partnerModifyModel, Integer partnerId) {
        // Recupera  e verifica che il partner esista
        PartnerModel partnerModel = get(partnerId);
        // Imposta i dati del Partner
        partnerModifyModel.setId(partnerId);
        // Salva i dati del Partner
        PartnerEntity partnerEntity = partnerManager.save(partnerModifyModel);

        return mapper.map(partnerEntity,PartnerModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer partnerId) {
        partnerManager.delete(partnerId);
    }

    @Override
    @Transactional
    public Page<ToolModel> getTools(int page, int pageSize, Integer partnerId) {
        return null;
    }

    @Override
    @Transactional
    public Page<LogisticModel> getLogistics(int page, int pageSize, Integer partnerId) {
        return null;
    }

    @Override
    @Transactional
    public Page<TeacherModel> getTeachers(int page, int pageSize, Integer partnerId) {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TeacherModel addTeacher(Integer teacherId,Integer partnerId) {
        // Verifico che il partner esista
        PartnerModel partnerModel = get(partnerId);
        /*
            Verifico che la Persona esista:
            ----Se non esiste lancio un'eccezione
         */
        PersonResponse personResponse = vilearRegistrationServiceClient.getPerson(teacherId);

        /*
            Verifica che il Docente non sia stato già inserito :
            ----Se non è inserito lancia un'eccezione
         */
        TeacherModel teacherModel = teacherService.get(teacherId, partnerId);
        if(teacherModel!=null){
            throw new TeacherAlreadyExistsException();
        } else{
            // Imposto i dati del Teacher
            teacherModel=new TeacherModel();
            teacherModel.setPartner(partnerModel);
            teacherModel.setId(personResponse.getId());
        }

        // Aggiunge il Docente
        return teacherService.add(teacherModel);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolModel addTool(Integer partnerId, ToolModel toolModel) {
        // Verifica se il partner esista
        PartnerModel partnerModel = get(partnerId);
        if(partnerModel.getAdmin()){
            toolModel.setPartner(partnerModel);
        }else{
            throw new PartnerIsNotAdmin();
        }
        // Setta il Partner che offre un Tool
        // Aggiunge il Tool
        return toolService.add(toolModel);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LogisticModel addLogistic(Integer partnerId, LogisticModel logisticModel) {
        // Verifica che il Partner esista
        PartnerModel partnerModel = get(partnerId);
        if(partnerModel.getAdmin()){
            logisticModel.setPartner(partnerModel);
        }else{
            throw new PartnerIsNotAdmin();
        }
        // Associa il Partner alla Logistica
        // Salva la Logistica
        return logisticService.add(logisticModel);
    }

    @Override
    @Transactional
    public void deleteTeacher(Integer teacherId, Integer partnerId) {
        // Elimina il Teacher
        teacherService.delete(teacherId,partnerId);
    }

    @Override
    @Transactional
    public void deleteTool(Integer toolId, Integer partnerId) {
        // Elimina il Tool
        toolService.delete(toolId,partnerId);
    }

    @Override
    @Transactional
    public void deleteLogistic(Integer logisticId, Integer partnerId) {
        //Se esite il logistic lo elimino
        logisticService.delete(logisticId,partnerId);
    }
}
