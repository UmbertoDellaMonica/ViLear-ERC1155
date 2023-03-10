package it.tcgroup.vilear.registration.Repository;

import it.tcgroup.vilear.registration.Entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Integer> {

    @Query("SELECT p from PersonEntity p where user_id=:userId")
    Optional<PersonEntity> findPersonByIdUser(Integer userId);

    @Query(nativeQuery = true,value = " select p.* " +
            " from " +
            " users u,persons p " +
            " where " +
            " p.user_id = u.id and " +
            " u.email=:email;")
    Optional<PersonEntity> getPersonByEmailAndPassword(@Param("email")String email);

}
