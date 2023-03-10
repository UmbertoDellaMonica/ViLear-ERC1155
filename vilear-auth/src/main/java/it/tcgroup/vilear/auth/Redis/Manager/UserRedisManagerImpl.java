package it.tcgroup.vilear.auth.Redis.Manager;

import it.tcgroup.vilear.auth.Model.UserJwt;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Repository.UserRedisRepository;
import it.tcgroup.vilear.base.Payload.Exception.NotAuthenticated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRedisManagerImpl implements UserRedisManager{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRedisRepository userRedisRepository;

    @Override
    public UserJwt save(UserJwtModel userRedisModel) {
        UserJwt userRedisEntity = mapper.map(userRedisModel,UserJwt.class);

        return userRedisRepository.save(userRedisEntity);
    }

    @Override
    public UserJwt get(Integer userId) {
        return userRedisRepository
                .findById(userId)
                .orElseThrow(()->new NotAuthenticated());
    }

    @Override
    public UserJwt getByToken(String token) {
        return userRedisRepository.findByToken(token).orElse(null);
    }

    @Override
    public UserJwt getByEmail(String email) {
        UserJwt userJwt = userRedisRepository.findByEmail(email).orElse(null);

        return userJwt;
    }

    @Override
    public void delete(Integer userId) {
        UserJwt userRedisEntity = get(userId);

        userRedisRepository.deleteById(userRedisEntity.getId());
    }
}
