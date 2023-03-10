package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.PartnerIsNotAssociated;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Repository.LogisticRepository;
import it.tcgroup.vilear.partner.Entity.LogisticEntity;
import it.tcgroup.vilear.base.Payload.Exception.LogisticNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LogisticManagerImpl implements LogisticManager {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LogisticRepository logisticRepository;

    private void checkPartner(LogisticEntity logisticEntity, Integer partnerId) {
        if(!logisticEntity.getPartner().getId().equals(partnerId)){
            throw new PartnerIsNotAssociated();
        }
    }


    @Override
    public LogisticEntity save(LogisticModel logisticModel) {
        LogisticEntity logisticEntity=mapper.map(logisticModel,LogisticEntity.class);

        return logisticRepository.save(logisticEntity);
    }

    @Override
    public LogisticEntity get(Integer logisticId, Integer partnerId) {
        LogisticEntity logisticEntity = logisticRepository
            .findById(logisticId)
            .orElseThrow(LogisticNotFoundException::new);
        // Verifica che il Partner sia associato
        checkPartner(logisticEntity,partnerId);
        return logisticEntity;
    }


    @Override
    public Page<LogisticEntity> findByPartner(Integer partnerId, PageRequest pageRequest){
        return logisticRepository.findByPartnerId(partnerId, pageRequest);
    }

    @Override
    public void delete(Integer logisticId, Integer partnerId){
        LogisticEntity logisticEntity = get(logisticId, partnerId);

        logisticRepository.deleteById(logisticEntity.getId());
    }

}
