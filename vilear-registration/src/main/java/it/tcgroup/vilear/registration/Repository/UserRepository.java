package it.tcgroup.vilear.registration.Repository;

import it.tcgroup.vilear.registration.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query("SELECT u from UserEntity u where email=:email")
    Optional<UserEntity> findUserByEmail(String email);

}
