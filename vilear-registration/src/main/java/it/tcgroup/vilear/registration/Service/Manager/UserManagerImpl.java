package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.UserNotFoundException;
import it.tcgroup.vilear.registration.Entity.UserEntity;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserModel userModel) {
        UserEntity userEntity = mapper.map(userModel,UserEntity.class);

        return  userRepository.save(userEntity);
    }

    @Override
    public UserEntity get(Integer userId){
        return userRepository
                .findById(userId)
                .orElseThrow(()-> new UserNotFoundException());
    }

    @Override
    public UserEntity getByEmail(String email){
        return userRepository
                .findUserByEmail(email)
                .orElse( null );
    }

    @Override
    public void delete(Integer userId){
        if(userRepository.existsById(userId)){
            userRepository.delete(get(userId));
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public Page<UserEntity> getAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

}
