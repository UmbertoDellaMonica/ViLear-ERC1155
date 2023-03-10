package it.tcgroup.vilear.partner.Service;

import it.tcgroup.vilear.partner.Entity.ToolEntity;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.partner.Repository.ToolRepository;
import it.tcgroup.vilear.partner.Service.Manager.ToolManager;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ToolServiceImpl implements ToolService{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolManager toolManager;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolModel add(ToolModel toolModel){
        ToolEntity toolEntity = toolManager.save(toolModel);

        return mapper.map(toolEntity,ToolModel.class);
    }

    @Override
    @Transactional
    public ToolModel get(Integer toolId,Integer partnerId){
        ToolEntity toolEntity = toolManager.get(toolId,partnerId);

        return mapper.map(toolEntity,ToolModel.class);
    }

    @Override
    @Transactional
    public PaginatedResponse<ToolModel> getByPartner(Integer partnerId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page,pageSize);
        Page<ToolEntity> toolEntityPage  = toolManager.findByPartner(partnerId,pageRequest);
        List<ToolModel> toolModelList= mapper.map(toolEntityPage.getContent(),new TypeToken<List<ToolModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData(
                toolEntityPage.getTotalPages(),
                toolEntityPage.getTotalElements()
        );
        PaginatedResponse<ToolModel>paginatedResponse=new PaginatedResponse<>(
                toolModelList,
                pageData
        );
        return paginatedResponse;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolModel update(ToolModel toolModifyModel, Integer toolId,Integer partnerId) {
        // Verifica i dati del Tool e se non esiste lancia un'eccezione
        ToolModel toolModel = get(toolId,partnerId);
        // Imposta i nuovi dati
        toolModifyModel.setPartner(toolModel.getPartner());
        toolModifyModel.setId(toolModel.getId());
        toolModifyModel.setCreatedAt(toolModel.getCreatedAt());
        // Aggiorna i dati che ho appena salvato
        ToolEntity toolEntity= toolManager.save(toolModifyModel);
        return mapper.map(toolEntity,ToolModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer toolId, Integer partnerId){
        toolManager.delete(toolId,partnerId);
    }

}
