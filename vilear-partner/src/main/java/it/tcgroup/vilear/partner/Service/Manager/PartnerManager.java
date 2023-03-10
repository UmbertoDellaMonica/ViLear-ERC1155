package it.tcgroup.vilear.partner.Service.Manager;


import it.tcgroup.vilear.partner.Entity.PartnerEntity;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import org.springframework.data.domain.Page;

public interface PartnerManager {

    /**
     * Salva i dati del Partner
     * @param partnerModel contiene i dati del Partner
     * @return i dati del Partner
     */
    PartnerEntity save(PartnerModel partnerModel);

    /**
     * Recupera il singolo Partner
     * @param partnerId id del partner
     */
    PartnerEntity get(Integer partnerId);

    /**
     * Recupera il Partner tramite l'id dell'utente
     * @param userId id dell'utente
     */
    PartnerEntity getByUser(Integer userId);

    /**
     * Elimina un singolo Partner
     * @param partnerId id del partner
     */
    void delete(Integer partnerId);

    /**
     * Recupera tutti i partner paginati
     * @param page indica il numero di pagine
     * @param pageSize indica la dimensione della pagina
     */
    Page<PartnerEntity> getAll(Integer page, Integer pageSize);


}
