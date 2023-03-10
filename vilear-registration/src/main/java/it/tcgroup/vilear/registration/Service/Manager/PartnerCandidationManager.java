package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.registration.Entity.PartnerCandidationEntity;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PartnerCandidationManager {

    /**
     * Salva i dati della candidatura del Partner
     * @param partnerCandidationModel contiene i dati della Candidatura del partner
     * @return i dati della candidatura del Partner
     */
    PartnerCandidationEntity save(PartnerCandidationModel partnerCandidationModel);

    PartnerCandidationEntity get(Integer partnerCandidationId);

    PartnerCandidationEntity getByUser(Integer userId);

    Page<PartnerCandidationEntity> getAll(PageRequest pageRequest);
}
