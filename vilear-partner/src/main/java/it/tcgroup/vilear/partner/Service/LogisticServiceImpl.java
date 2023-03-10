package it.tcgroup.vilear.partner.Service;

import it.tcgroup.vilear.partner.Entity.LogisticEntity;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Service.Manager.LogisticManager;
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
public class LogisticServiceImpl implements LogisticService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LogisticManager logisticManager;

    @Override
    @Transactional
    public LogisticModel add(LogisticModel logisticModel) {
        // Aggiunge i dati della Logistica
        LogisticEntity logisticEntity = logisticManager.save(logisticModel);

        return mapper.map(logisticEntity,LogisticModel.class);
    }

    @Override
    @Transactional
    public LogisticModel get(Integer logisticId, Integer partnerId){
        LogisticEntity logisticEntity = logisticManager.get(logisticId,partnerId);

        return mapper.map(logisticEntity,LogisticModel.class);
    }

    @Override
    @Transactional
    public PaginatedResponse<LogisticModel> getByPartnerId(Integer partnerId, int page, int pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        Page<LogisticEntity> logisticEntityPage = logisticManager.findByPartner(partnerId, pageRequest);
        List<LogisticModel> logisticModelList = mapper.map(logisticEntityPage.getContent(), new TypeToken<List<LogisticModel>>(){}.getType());;
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(logisticEntityPage.getTotalElements());
        pageData.setTotalPages(logisticEntityPage.getTotalPages());
        return new PaginatedResponse<>(
            logisticModelList,
            pageData
        );
    }

    @Override
    @Transactional
    public LogisticModel update(LogisticModel logisticModifyModel, Integer logisticId, Integer partnerId) {
        //Verifico che quell'elemento della logistica ci sia oppure no
        LogisticModel logisticModel = get(logisticId, partnerId);
        // Imposto i dati della Logistica
        logisticModifyModel.setId(logisticId);
        logisticModifyModel.setPartner(logisticModel.getPartner());
        logisticModifyModel.setCreatedAt(logisticModel.getCreatedAt());
        // Aggiorno i dati della Logistica Modificata
        LogisticEntity logisticEntity = logisticManager.save(logisticModifyModel);

        return mapper.map(logisticEntity, LogisticModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer logisticId,Integer partnerId) {
       logisticManager.delete(logisticId, partnerId);
    }

}
