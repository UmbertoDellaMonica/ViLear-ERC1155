package it.tcgroup.vilear.partner.Service;

import it.tcgroup.vilear.partner.Entity.TeacherEntity;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Service.Manager.TeacherManager;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherManager teacherManager;

    @Override
    @Transactional
    public TeacherModel add(TeacherModel teacherModel) {
        // Salvo i dati del Teacher
        TeacherEntity teacherEntity = teacherManager.save(teacherModel);

        return mapper.map(teacherEntity,TeacherModel.class);
    }

    @Override
    @Transactional
    public TeacherModel get(Integer personId, Integer partnerId) {
        /*
            Verifico se esiste il docente con quel determinato Id
            ->VERO lo restituisco
            ->FALSO lancio un'eccezione NOT FOUND
         */
        TeacherEntity teacherEntity = teacherManager.get(personId, partnerId);
        if (teacherEntity == null) {
            return null;
        } else {
            return mapper.map(teacherEntity, TeacherModel.class);
        }
    }

    @Override
    @Transactional
    public PaginatedResponse<TeacherModel> getByPartner(Integer partnerId, Integer page, Integer pageSize) {
            PageRequest pageRequest= PaginationUtils.buildPageRequest(page,pageSize);
            Page<TeacherEntity> teacherEntityPage = teacherManager.findByPartner(partnerId,pageRequest);
            List<TeacherModel> teacherModelList = mapper.map(teacherEntityPage.getContent(),
                    new TypeToken<List<TeacherModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData(
                teacherEntityPage.getTotalPages(),
                teacherEntityPage.getTotalElements()
        );
            return new PaginatedResponse<>(
                    teacherModelList,
                    pageData
            );
    }

    @Override
    @Transactional
    public void delete(Integer  partnerId, Integer teacherId ) {
        teacherManager.delete(teacherId, partnerId);
    }

}
