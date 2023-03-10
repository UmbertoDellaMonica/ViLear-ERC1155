package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.PartnerCandidationNotFoundException;
import it.tcgroup.vilear.registration.Entity.PartnerCandidationEntity;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Repository.PartnerCandidationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PartnerCandidationManagerImpl implements PartnerCandidationManager {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PartnerCandidationRepository partnerCandidationRepository;

    @Override
    public PartnerCandidationEntity save(PartnerCandidationModel partnerCandidationModel) {
        PartnerCandidationEntity partnerCandidationEntity = mapper.map(partnerCandidationModel, PartnerCandidationEntity.class);

        return partnerCandidationRepository.save(partnerCandidationEntity);
    }

    @Override
    public PartnerCandidationEntity get(Integer partnerCandidationId) {
        return partnerCandidationRepository
                .findById(partnerCandidationId)
                .orElseThrow(()->new PartnerCandidationNotFoundException());
    }

    @Override
    public PartnerCandidationEntity getByUser(Integer userId) {
        return partnerCandidationRepository
                .findByUser(userId)
                .orElseThrow(()->new PartnerCandidationNotFoundException());
    }

    @Override
    public Page<PartnerCandidationEntity> getAll(PageRequest pageRequest) {
        return partnerCandidationRepository.findAll(pageRequest);
    }
}
