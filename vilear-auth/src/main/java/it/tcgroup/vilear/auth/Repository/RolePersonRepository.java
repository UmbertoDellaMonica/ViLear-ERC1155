package it.tcgroup.vilear.auth.Repository;

import it.tcgroup.vilear.auth.Entity.RolePersonEntity;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePersonRepository extends JpaRepository<RolePersonEntity, RolePersonEntity.PrimaryKey> {

    @Query("SELECT r from RolePersonEntity r where person_id=:personId ")
    Page<RolePersonEntity> findByUser(Integer personId, PageRequest pageRequest);

    @Query("SELECT r from RolePersonEntity r where r.primaryKey.roleCode = :roleCode AND r.primaryKey.personId = :personId")
    Optional<RolePersonEntity> findByIdAndRole(Integer personId, RoleEnum roleCode);

    @Query("SELECT r from RolePersonEntity r where role_code=:roleCode")
    Page<RolePersonEntity> findByRole(String roleCode, PageRequest pageRequest);
}
