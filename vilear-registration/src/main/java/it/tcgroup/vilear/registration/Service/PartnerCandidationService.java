package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import org.springframework.transaction.annotation.Transactional;

public interface PartnerCandidationService {

    /**
     * Candida un Partner
     * @param partnerCandidationModel contiene i dati della Candidatura del partner
     * @return la Candidatura del Partner
     */
    PartnerCandidationModel candidate(PartnerCandidationModel partnerCandidationModel);

    /**
     * Accettazione/Rifiuto della candidatura del Partner
     * @param userId id della candidatura
     * @param decision  Accettazione/Rifiuto
     * @return una Candidatura del Partner Accettata/Rifiutata
     */
    PartnerCandidationModel choose(Integer userId, DecisionEnum decision);

    /**
     * Recupera una singola Candidatura
     * @param partnerCandidationId id della Candidatura del partner
     * @return una singola Candidatura
     */
    PartnerCandidationModel get(Integer partnerCandidationId);

    PartnerCandidationModel getByUser(Integer userId);

    /**Recupera tutte Candidature dei Partner
     * @param page pagina da visualizzare
     * @param pageSize numero degli elementi da visualizzare
     * @return  una Lista di Candidature
 */
    PaginatedResponse<PartnerCandidationModel> getPartnerCandidations(Integer page, Integer pageSize);


}
