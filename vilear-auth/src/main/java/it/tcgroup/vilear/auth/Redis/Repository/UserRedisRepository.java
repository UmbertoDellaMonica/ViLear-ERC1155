package it.tcgroup.vilear.auth.Redis.Repository;


import it.tcgroup.vilear.auth.Model.UserJwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@EnableRedisRepositories
public interface UserRedisRepository extends CrudRepository<UserJwt,Integer> {

    @Query("SELECT u from UserRedisEntity u where token=:token")
    Optional<UserJwt> findByToken(@Param("token") String token);

    @Query("SELECT u from UserRedisEntity u where email=:email")
    Optional<UserJwt> findByEmail(@Param("email") String email);
}
