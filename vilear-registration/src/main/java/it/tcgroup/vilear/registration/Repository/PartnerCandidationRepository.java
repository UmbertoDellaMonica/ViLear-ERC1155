package it.tcgroup.vilear.registration.Repository;

import it.tcgroup.vilear.registration.Entity.PartnerCandidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerCandidationRepository extends JpaRepository<PartnerCandidationEntity,Integer> {

    @Query("SELECT p from PartnerCandidationEntity p where user_id=:userId")
    Optional<PartnerCandidationEntity> findByUser(@Param("userId")Integer userId);

}
