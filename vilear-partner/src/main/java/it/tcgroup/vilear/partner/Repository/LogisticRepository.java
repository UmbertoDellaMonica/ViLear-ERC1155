package it.tcgroup.vilear.partner.Repository;

import it.tcgroup.vilear.partner.Entity.LogisticEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogisticRepository extends JpaRepository<LogisticEntity,Integer> {

    /**
     * Recupera tutta la Logistica relativa ad un Partner
     * @param partnerId id del partner
     * @param pageRequest contiene le info per la Paginazione
     * @return
     */
    @Query("SELECT l from LogisticEntity l where partner_id = :partnerId")
    Page<LogisticEntity> findByPartnerId(Integer partnerId, PageRequest pageRequest);
}
