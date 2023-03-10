package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.partner.Entity.ToolEntity;
import it.tcgroup.vilear.partner.Model.ToolModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ToolManager {
    /**
     * Aggiunge un Tool
     * @param toolModel contiene i dati del Tool
     */
    ToolEntity save(ToolModel toolModel);

    /**
     * Recupera un singolo Tool
     * @param toolId id del Tool
     * @param partnerId id del Partner
     * @return
     */
    ToolEntity get(Integer toolId, Integer partnerId);

    /**
     * Recupera la Paginazione dei Tool Associata al Partner
     * @param partnerId id del Partner
     * @param pageRequest contiene la Paginazione dei Tool
     */
    Page<ToolEntity> findByPartner(Integer partnerId, PageRequest pageRequest);

    /**
     * Elimina il tool tramite
     * @param toolId id del Tool
     * @param partnerId id del Partner
     */
    void delete(Integer toolId, Integer partnerId);

}
