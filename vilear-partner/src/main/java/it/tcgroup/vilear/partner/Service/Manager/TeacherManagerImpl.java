package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.PartnerIsNotAssociated;
import it.tcgroup.vilear.partner.Entity.TeacherEntity;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TeacherManagerImpl implements TeacherManager{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherRepository teacherRepository;

    private void checkPartner(TeacherEntity teacherEntity,Integer partnerId){
        if(!teacherEntity.getPartner().getId().equals(partnerId)){
            throw new PartnerIsNotAssociated();
        }
    }

    @Override
    public TeacherEntity save(TeacherModel teacherModel) {

        TeacherEntity teacherEntity = mapper.map(teacherModel,TeacherEntity.class);
        teacherEntity.setId(teacherModel.getId());
        // Aggiorna i dati
        return teacherRepository.save(teacherEntity);
    }

    @Override
    public TeacherEntity get(Integer personId, Integer partnerId){
        TeacherEntity teacherEntity = teacherRepository.findById(personId).orElse(null);
        if(teacherEntity == null){
            return null;
        }
        checkPartner(teacherEntity,partnerId);
        return teacherEntity;
    }

    @Override
    public Page<TeacherEntity> findByPartner(Integer partnerId, PageRequest pageRequest) {
        return teacherRepository.findByPartner(partnerId,pageRequest);
    }

    @Override
    public void delete(Integer teacherId, Integer partnerId){
        TeacherEntity teacherEntity = get(teacherId,partnerId);

        teacherRepository.delete(teacherEntity);
    }

}
