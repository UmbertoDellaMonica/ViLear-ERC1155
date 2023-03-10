package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.registration.Entity.PersonEntity;
import it.tcgroup.vilear.registration.Model.PersonModel;
import it.tcgroup.vilear.registration.Service.Manager.PersonManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonManager personManager;

    @Override
    @Transactional
    public PersonModel add(PersonModel personModel) {
        PersonEntity personEntity = personManager.save(personModel);

        return mapper.map(personEntity, PersonModel.class);
    }

    @Override
    @Transactional
    public PersonModel get(Integer personId) {
        PersonEntity personEntity = personManager.get(personId);

        return mapper.map(personEntity, PersonModel.class);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PersonModel update(PersonModel newPersonModel, Integer personId) {
        // Verifica se l'utente esiste davvero altrimenti lancia un'eccezione
        PersonModel personModel = get(personId);
        // Imposta i dati
        newPersonModel.setId(personId);
        newPersonModel.setCreatedAt(personModel.getCreatedAt());
        newPersonModel.setUser(personModel.getUser());

        PersonEntity personEntity = personManager.save(newPersonModel);

        return mapper.map(personEntity, PersonModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer personId) {
       personManager.delete(personId);
    }

}
