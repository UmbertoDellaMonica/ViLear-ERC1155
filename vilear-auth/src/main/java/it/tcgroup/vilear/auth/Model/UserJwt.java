package it.tcgroup.vilear.auth.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "User",timeToLive = 300)
public class UserJwt implements Serializable {

    @Id
    private Integer id;
    @Indexed
    private String email;

    private String password;

    @Indexed
    private String token;

    private Date releaseDate;

    private Date expiredDate;

}
