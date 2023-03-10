package it.tcgroup.vilear.registration.Repository;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, RoleEnum> {

    @Query("SELECT r from RoleEntity r where role_label=:roleLabel")
    Optional<RoleEntity>findByRoleLabel(String roleLabel);

}
