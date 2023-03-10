package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.AgendaNotFoundException;
import it.tcgroup.vilear.base.Payload.Exception.CourseIsNotAssociated;
import it.tcgroup.vilear.course.Entity.AgendaEntity;
import it.tcgroup.vilear.course.Model.AgendaModel;
import it.tcgroup.vilear.course.Repository.AgendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaManagerImpl implements AgendaManager{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AgendaRepository agendaRepository;

    private void checkCourse(AgendaEntity agendaEntity, Integer courseId) {
        if(!agendaEntity.getCourse().getId().equals(courseId)){
            throw new CourseIsNotAssociated();
        }
    }

    @Override
    public AgendaEntity save(AgendaModel agendaModel) {
        // Salva i dati dell'Agenda di un corso
        AgendaEntity agendaEntity = mapper.map(agendaModel,AgendaEntity.class);

        return agendaRepository.save(agendaEntity);
    }

    @Override
    public AgendaEntity get(Integer agendaId, Integer courseId){
        AgendaEntity agendaEntity = agendaRepository.findById(agendaId)
                .orElseThrow(()->new AgendaNotFoundException());
        checkCourse(agendaEntity,courseId);
        return agendaEntity;
    }


    @Override
    public Page<AgendaEntity> getByCourse(Integer courseId,PageRequest pageRequest){
        return agendaRepository.findByCourse(courseId,pageRequest);
    }

    @Override
    public void delete(Integer agendaId, Integer courseId){
        AgendaEntity agendaEntity = get(agendaId,courseId);

        agendaRepository.delete(agendaEntity);
    }

    @Override
    public AgendaEntity getByAgendaAndCourse(Integer agendaId, Integer courseId) {
        return agendaRepository
                .findByAgendaIdAndCourseId(agendaId, courseId)
                .orElseThrow(()->new AgendaNotFoundException());
    }

    @Override
    public Page<AgendaEntity> findAll(PageRequest pageRequest) {
        return agendaRepository.findAll(pageRequest);
    }

}
