package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.PartnerIsNotAssociated;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.partner.Repository.ToolRepository;
import it.tcgroup.vilear.partner.Entity.ToolEntity;
import it.tcgroup.vilear.base.Payload.Exception.ToolNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ToolManagerImpl implements ToolManager{
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ModelMapper mapper;

    private void checkPartner(ToolEntity toolEntity, Integer partnerId) {
        if(!toolEntity.getPartner().getId().equals(partnerId)){
            throw new PartnerIsNotAssociated();
        }
    }


    @Override
    public ToolEntity save(ToolModel toolModel) {
        ToolEntity toolEntity = mapper.map(toolModel,ToolEntity.class);

        return toolRepository.save(toolEntity);
    }

    @Override
    public ToolEntity get(Integer toolId,Integer partnerId){
        ToolEntity toolEntity = toolRepository
                .findById(toolId)
                .orElseThrow(()->new ToolNotFoundException());
        checkPartner(toolEntity,partnerId);
        return toolEntity;
    }



    @Override
    public Page<ToolEntity> findByPartner(Integer partnerId, PageRequest pageRequest){
        return toolRepository.findByPartner(partnerId,pageRequest);
    }

    @Override
    public void delete(Integer toolId,Integer partnerId){
      ToolEntity toolEntity =  get(toolId,partnerId);

      toolRepository.deleteById(toolEntity.getId());
    }


}
