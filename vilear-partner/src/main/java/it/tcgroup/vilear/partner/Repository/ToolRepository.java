package it.tcgroup.vilear.partner.Repository;

import it.tcgroup.vilear.partner.Entity.ToolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<ToolEntity,Integer> {

    /**
     * Recupera tutti Tool associati al Partner
     * @param partnerId id del Partner
     * @param pageRequest contiene le info sulla Paginazione
     */
    @Query("SELECT t from ToolEntity t where partner_id=:partnerId")
    Page<ToolEntity> findByPartner(Integer partnerId, PageRequest pageRequest);

}
