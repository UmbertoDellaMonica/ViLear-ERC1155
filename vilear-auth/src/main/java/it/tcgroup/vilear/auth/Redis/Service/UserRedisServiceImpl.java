package it.tcgroup.vilear.auth.Redis.Service;

import it.tcgroup.vilear.auth.Model.UserJwt;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Manager.UserRedisManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;

@Service
public class UserRedisServiceImpl implements UserRedisService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRedisManager userRedisManager;



    @Override
    public UserJwtModel add(UserJwtModel userRedisModel) {
        UserJwt UserJwt = userRedisManager.save(userRedisModel);

        return mapper.map(UserJwt,UserJwtModel.class);
    }

    @Override
    public UserJwtModel get(Integer userId) {
        UserJwt UserJwt = userRedisManager.get(userId);

        return mapper.map(UserJwt,UserJwtModel.class);
    }

    @Override
    public UserJwtModel getByToken(String token) {
        UserJwt userJwt = userRedisManager.getByToken(token);
        if(userJwt == null){
            return null;
        }

        return mapper.map(userJwt,UserJwtModel.class);
    }

    @Override
    public UserJwtModel getByEmail(String email) {
        UserJwt userJwt = userRedisManager.getByEmail(email);
        if(userJwt==null) {
            return null;
        }

        return mapper.map(userJwt,UserJwtModel.class);
    }

    @Override
    public Boolean isExpiredTokenFromUser(UserJwtModel userJwtModel) {
        if(userJwtModel == null) {
            return Boolean.TRUE;
        }
        Date now = Date.from(Instant.now());
        if(now.after(userJwtModel.getExpiredDate())){
            // è scaduto il Token
            return Boolean.TRUE;
            // Dovrei eliminare il token
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean deleteUser(UserJwtModel userRedisModel) {
        userRedisManager.delete(userRedisModel.getId());
        return Boolean.TRUE;
    }

}
