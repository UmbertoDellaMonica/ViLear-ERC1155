package it.tcgroup.vilear.partner.Repository;

import it.tcgroup.vilear.partner.Entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Integer> {

    /**
     * Recupera tutti Docenti relativi ad un Partner
     * @param partnerId id del Partner
     * @param pageRequest contiene le info per la Paginazione
     */
    @Query("SELECT t from TeacherEntity t where partner_id=:partnerId")
    Page<TeacherEntity> findByPartner(Integer partnerId, PageRequest pageRequest);

}
