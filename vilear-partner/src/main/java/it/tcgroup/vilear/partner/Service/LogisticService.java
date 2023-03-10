package it.tcgroup.vilear.partner.Service;

import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import org.springframework.transaction.annotation.Transactional;

public interface LogisticService {

    /**
     * Aggiunge una Logistica
     * @param logisticModel contiene i dati della logistica
     * @return una singola Logistica
     */
    LogisticModel add(LogisticModel logisticModel);

    /**
     * Recupero di una singola Logistica
     * @param logisticId id della Logistica
     * @param partnerId id del Partner
     */
    LogisticModel get(Integer logisticId, Integer partnerId);

    /**
     * Recupera una logistica per un Partner
     * @param partnerId partnerId id del partner
     * @param page
     * @param pageSize
     * @return una singola Logistica
     */
    PaginatedResponse<LogisticModel> getByPartnerId(Integer partnerId, int page, int pageSize);

    /**
     * Aggiorna i dati della logistica
     * @param logisticModifyModel contiene i dati di una logistica
     * @param logisticId  id della logistica
     * @return una Logistica aggiornata
     */
    LogisticModel update(LogisticModel logisticModifyModel, Integer logisticId, Integer partnerId);

    /**
     * Elimina una singola Logistica
     *
     * @param logisticId id della logistica
     * @param partnerId id della logistica
     */
    void delete(Integer logisticId, Integer partnerId);

}
