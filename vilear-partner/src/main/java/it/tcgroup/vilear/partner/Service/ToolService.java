package it.tcgroup.vilear.partner.Service;


import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface ToolService {

    /**
     * Aggiunge un Tool
     * @param toolModel contiene i dati  del Tool
     * @return un Tool nuovo
     */
    ToolModel add(ToolModel toolModel);

    /**
     * Recupera un Tool attraverso
     * @param partnerId id del Partner
     * @param toolId id del Tool
     */

    ToolModel get(Integer toolId, Integer partnerId);

    /**
     * Recupera un Tool tramite l'id del Partner
     *
     * @param partnerId id del partner
     * @param page      pagina  del tool
     * @param pageSize  dimensione della pagina
     * @return un Tool tramite l'id del partner
     */
    PaginatedResponse<ToolModel> getByPartner(Integer partnerId, Integer page, Integer pageSize);

    /**
     * Aggiorna un Tool del Partner tramite :
     * @param toolModifyModel contiene i nuovi dati del toolModel
     * @param toolId  id del tool
     * @param partnerId  id del Partner
     * @return un Tool modificato
     */
    ToolModel update(ToolModel toolModifyModel, Integer toolId, Integer partnerId);

    /**
     * Elimina un Tool
     * @param toolId    id del Tool
     * @param partnerId id del Partner
     */
    void delete(Integer toolId, Integer partnerId);


}
