package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.partner.Entity.PartnerEntity;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Repository.PartnerRepository;
import it.tcgroup.vilear.base.Payload.Exception.PartnerNotFoundException;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PartnerManagerImpl implements PartnerManager{

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PartnerEntity save(PartnerModel partnerModel) {
        PartnerEntity partnerEntity=mapper.map(partnerModel,PartnerEntity.class);

        return partnerRepository.save(partnerEntity);
    }

    @Override
    public PartnerEntity get(Integer partnerId){
        return partnerRepository
                .findById(partnerId)
                .orElseThrow(()->new PartnerNotFoundException());
    }

    @Override
    public PartnerEntity getByUser(Integer userId) {
        return partnerRepository
                .findByUserId(userId)
                .orElse(null);
    }

    @Override
    public void delete(Integer partnerId){
        if(partnerRepository.existsById(partnerId)){
            // Elimina Dati del Partner
            partnerRepository.deleteById(partnerId);
        }else{
            throw new PartnerNotFoundException();
        }
    }

    @Override
    public Page<PartnerEntity> getAll(Integer page, Integer pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        return partnerRepository.findAll(pageRequest);
    }

}
