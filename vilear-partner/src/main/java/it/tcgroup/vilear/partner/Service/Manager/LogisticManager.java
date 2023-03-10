package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.partner.Entity.LogisticEntity;
import it.tcgroup.vilear.partner.Model.LogisticModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LogisticManager {

    /**
     * Salva i dati della Logistica associata al Corso
     * @param logisticModel contiene i dati della Logistica
     * @return una Logistica
     */
    LogisticEntity save(LogisticModel logisticModel);

    /**
     * Recupera la singola Logistica
     *
     * @param logisticId id della Logistica
     * @param partnerId id del partner
     */
    LogisticEntity get(Integer logisticId, Integer partnerId);

    /**
     * Recupera la Lista della Logistica associata che posso recuperare dal Partner
     * @param partnerId id del Partner
     * @param pageRequest contiene i dati della Paginazione
     * @return
     */
    Page<LogisticEntity> findByPartner(Integer partnerId, PageRequest pageRequest);

    /**
     * Elimina la Logistica
     * @param logisticId id della Logistica
     * @param partnerId id del Partner
     */
    void delete(Integer logisticId, Integer partnerId);

}
