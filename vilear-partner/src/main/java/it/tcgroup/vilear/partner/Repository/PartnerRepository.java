package it.tcgroup.vilear.partner.Repository;

import it.tcgroup.vilear.partner.Entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity,Integer> {
    /**
     * Recupera il Partner tramite lo UserId
     * @param userId id dell'utente
     */
    @Query("SELECT p from PartnerEntity p where user_id=:userId")
    Optional<PartnerEntity> findByUserId(Integer userId);
}
