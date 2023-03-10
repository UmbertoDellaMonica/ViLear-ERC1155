package it.tcgroup.vilear.registration.Repository;

import it.tcgroup.vilear.registration.Entity.UserInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitationEntity,Integer> {

    @Query("SELECT u from UserInvitationEntity u where invitation_token=:token")
    Optional<UserInvitationEntity> findByToken(@Param("token") UUID token);

    @Query("SELECT u from UserInvitationEntity u where user_id=:userId")
    Optional<UserInvitationEntity> findByUser(@Param("userId") Integer userId);
}
