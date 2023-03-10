package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.registration.Entity.UserEntity;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.Manager.UserManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    @Override
    @Transactional
    public UserModel add(UserModel userModel){
        UserEntity userEntity= userManager.save(userModel);

        return mapper.map(userEntity, UserModel.class);
    }

    @Override
    @Transactional
    public UserModel get(Integer userId) {
        // Recupera i dati dell'utente
        UserEntity userEntity = userManager.get(userId);

        return mapper.map(userEntity, UserModel.class);
    }

    @Override
    @Transactional
    public UserModel getByEmail(String email) {
        // Verifica che l'utente esista tramite la sua email
        UserEntity userEntity = userManager.getByEmail(email);
        if (userEntity == null) {
            return null;
        } else {
            return mapper.map(userEntity, UserModel.class);
        }
    }

    @Override
    @Transactional
    public PaginatedResponse<UserModel> getUsers(int page, int PageSize) {
        PageRequest pageRequest= PaginationUtils.buildPageRequest(page,PageSize);
        Page<UserEntity> userEntityList= userManager.getAll(pageRequest);
        List<UserModel> userModelList = mapper.map(userEntityList.getContent(),new TypeToken<List<UserModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(userEntityList.getTotalElements());
        pageData.setTotalPages(userEntityList.getTotalPages());

        PaginatedResponse<UserModel> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setData(userModelList);
        paginatedResponse.setPageData(pageData);

        return paginatedResponse;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserModel update(UserModel userModifyModel, Integer userId) {

        // Verifica che l'utente esista
        UserModel userModel = get(userId);

        // Imposta i dati dell'utente
        userModel.setPassword(passwordEncoder.encode(userModifyModel.getPassword()));
        userModel.setEmail(userModifyModel.getEmail());
        // Aggiorna l'utente
        UserEntity userEntity = userManager.save(userModel);
        return mapper.map(userEntity, UserModel.class);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Integer userId) {
        userManager.delete(userId);
    }

}
